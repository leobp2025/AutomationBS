document.getElementById('excelFile').addEventListener('change', function(e) {
    const fileName = e.target.files[0] ? e.target.files[0].name : 'Ningún archivo seleccionado';
    document.getElementById('fileName').textContent = fileName;
});

async function ejecutar(tipoAutomatizacion) {
    const excelFile = document.getElementById('excelFile').files[0];
    
    if (!excelFile) {
        mostrarError('⚠️ Debes seleccionar un archivo Excel antes de ejecutar');
        return;
    }

    const formData = new FormData();
    formData.append('file', excelFile);

    mostrarCargando();
    limpiarLogs();
    agregarLog('Iniciando ' + tipoAutomatizacion + '...', 'info');

    try {
        const response = await fetch(`/api/automatizaciones/ejecutar/${tipoAutomatizacion}`, {
            method: 'POST',
            body: formData
        });

        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status}`);
        }

        const data = await response.json();
        mostrarExito(data);
        agregarLog(`✅ ${tipoAutomatizacion} completada exitosamente`, 'success');
    } catch (error) {
        mostrarError(`❌ Error: ${error.message}`);
        agregarLog(`Error: ${error.message}`, 'error');
    }
}

function mostrarCargando() {
    const resultado = document.getElementById('resultado');
    resultado.innerHTML = '<p class="status-loading">⏳ Ejecutando automatización...</p>';
}

function mostrarExito(data) {
    const resultado = document.getElementById('resultado');
    const mensaje = data.mensaje || 'Automatización completada exitosamente';
    const detalles = data.detalles ? `<p>${data.detalles}</p>` : '';
    resultado.innerHTML = `<p class="status-success">✅ ${mensaje}</p>${detalles}`;
}

function mostrarError(mensaje) {
    const resultado = document.getElementById('resultado');
    resultado.innerHTML = `<p class="status-error">${mensaje}</p>`;
}

function agregarLog(mensaje, tipo = 'info') {
    const logsContent = document.getElementById('logsContent');
    const timestamp = new Date().toLocaleTimeString('es-AR');
    
    const logEntry = document.createElement('div');
    logEntry.className = 'log-entry';
    
    let tipoClass = '';
    if (tipo === 'success') tipoClass = 'log-success';
    if (tipo === 'error') tipoClass = 'log-error';
    
    logEntry.innerHTML = `
        <span class="log-time">[${timestamp}]</span>
        <span class="log-message ${tipoClass}">${mensaje}</span>
    `;
    
    logsContent.appendChild(logEntry);
    logsContent.scrollTop = logsContent.scrollHeight;
}

function limpiarLogs() {
    document.getElementById('logsContent').innerHTML = '';
}
