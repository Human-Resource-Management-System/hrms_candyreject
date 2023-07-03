<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" type="text/css" href="./css/UploadReferenceDocument.css">
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
 <div class="form-container">
    <h1>Upload Document</h1>

<form id="document-form"  >
  

    <div class="form-group">
      <label for="category">Category:</label>
      <select id="category" name="category">
        <option value="Employee Handbook">Employee Handbook</option>
        <option value="Code of Conduct">Code of Conduct</option>
        <option value="Anti Harassment Policy">Anti Harassment Policy</option>
        <option value="IT Security Policy">IT Security Policy</option>
        <option value="Travel and Expense Policy">Travel and Expense Policy</option>
      </select>
    </div>

    <div class="form-group">
      <label for="file-upload">Upload Document:</label>
      <input type="file" id="file-upload" name="documentData" required>
    </div>

    <input class="submit-button" type="submit" value="Upload">
  </form>

  <script>
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
  </script>

</body>
</html>
