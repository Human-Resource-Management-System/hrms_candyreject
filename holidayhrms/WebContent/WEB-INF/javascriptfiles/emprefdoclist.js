 function filterDocumentsByCategory() {
      var category = document.getElementById("category").value;

      // Show/hide the corresponding categories based on the selected option
      var categories = ["code-of-conduct", "employee-handbook", "Anti-Harassment-Policy", "IT-Security-Policy", "Travel-and-Expense-Policy"];
      for (var i = 0; i < categories.length; i++) {
        var categoryId = categories[i];
        if (category === "all" || category === categoryId) {
          document.getElementById(categoryId).style.display = "block";
        } else {
          document.getElementById(categoryId).style.display = "none";
        }
      }
    }

    function showMessage(message) {
      // Assuming you have a message element in your HTML, e.g., <div id="message"></div>
      const messageElement = document.getElementById('message');
      messageElement.textContent = message;
      messageElement.style.display = 'block';
    }

    function updateUI(documentId) {
      const documentElement = document.getElementById(documentId); // Find the document element to be removed
      if (documentElement) {
        documentElement.remove(); // Remove the document element from the DOM
        refreshPage();
      }
    }

    function refreshPage() {
      location.reload(); // Reload the current page
    }