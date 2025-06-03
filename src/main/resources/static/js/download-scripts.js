window.addEventListener('load', () => {
    const id = document.getElementById('fileID').innerText.trim();
    const downloadButton = document.getElementById('downloadButton');
    const removeButton = document.getElementById('removeButton');
    const downloadForm = document.querySelector('.download-form');

    downloadButton.addEventListener('click', () => {
        downloadForm.classList.add('download-form_success');
        setTimeout(() => window.location.href = '/api/v1/file/?id=' + id, 500);
    });

    removeButton.addEventListener('click', () => {
        const data = new FormData();
        data.set("id", id);
        fetch('/api/v1/file/remove/', {
            method: 'post',
            body: data
        }).then(() => window.location.href = '/download/?removed=true');
    });
});