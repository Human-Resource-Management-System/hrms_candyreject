function payroll() {
  // Show the loading spinner
  $("#msg").html('<div class="loading-spinner"></div>');

  $.ajax({
    type: "POST",
    url: "getpayslip",
    data: $("#payrollemp").serialize(),
    success: function(response) {
      // Hide the loading spinner
      $("#msg").empty();

      var containerDiv = $(".main");
      containerDiv.html(response);
    },
    error: function() {
      // Hide the loading spinner
      $("#msg").empty();

      $("#msg").text("Error Occurred");
      alert("Error occurred. Please try again later.");
    }
  });
}