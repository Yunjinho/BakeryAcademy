// Calculate the date on page load
var currentDate = new Date();
var nextDay = new Date(currentDate.getTime() + (24 * 60 * 60 * 1000));  // add one day

document.getElementById('dateNormal').innerText = nextDay.toLocaleDateString();
document.getElementById('dateCool').innerText = nextDay.toLocaleDateString();

// Display the tooltip on mouseover
document.getElementById('order-info').addEventListener('mouseover', function() {
    document.getElementById('tooltip').style.display = 'block';
});

// Hide the tooltip on mouseout
document.getElementById('order-info').addEventListener('mouseout', function() {
    document.getElementById('tooltip').style.display = 'none';
});
