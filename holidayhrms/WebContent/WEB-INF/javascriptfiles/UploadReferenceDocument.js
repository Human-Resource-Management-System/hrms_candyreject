  
  const form = document.getElementById('document-form');
    form.addEventListener('submit', function(event) {
      event.preventDefault();

      const category = document.getElementById('category').value;
      const documentData = document.getElementById('file-upload').files[0];
  
      if (!category || !documentData) {
        alert('Please fill in all the required fields and upload a document.');
        return;
      }

      const formData = new FormData();
      formData.append('category', category);
      formData.append('documentData', documentData);
      
      fetch('DocumentSave', {
        method: 'POST',
        body: formData
      })
      .then(response => {
        if (response.ok) {
          alert('Document uploaded successfully!');
          form.reset();
        } else {
          throw new Error('Error uploading document.');
        }
      })
      .catch(error => {
        alert(error.message);
      });
    });
 