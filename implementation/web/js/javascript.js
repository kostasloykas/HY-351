/* user login */
function loginGET() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) { /* simple user */
            window.location.replace("account.html");

        } else if (xhr.status === 201) { /* admin */
            window.location.replace("admin.html");
        } else if (xhr.status === 203) { /* nurse */
            window.location.replace("nurse.html");
        } else if (xhr.status === 202) { /* doctor */
            window.location.replace("doctor.html");
        } else {
            $("#ajaxContent").html("Wrong username or password. Note that if you are a doctor you must be certified.");

        }
    };
    var data = $('#Form').serialize();
    xhr.open('POST', 'Login?' + data);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

/* get values to use in chart */
function visualize_differences() {
    var xhr = new XMLHttpRequest();
    
    var labels = [];
    var values = [];
    
    labels.push("Unvaccinated");
    labels.push("Fully vaccinated");
    labels.push("Semi vaccinated");
    
    
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const obj = JSON.parse(xhr.responseText);
            document.getElementById('visualize_dif').innerHTML = "<b>Unvaccinated: </b>" + obj[0];
            document.getElementById('visualize_dif').innerHTML += "<br><b>Fully vaccinated: </b>" + obj[1];
            document.getElementById('visualize_dif').innerHTML += "<br><b>Semi vaccinated: </b>" + obj[2];
            
            values.push(obj[0]);
            values.push(obj[1]);
            values.push(obj[2]);
            
            drawChart(labels, values);
        } else if (xhr.status !== 200) {
            document.getElementById('visualize_dif').innerHTML = "No available data.";

        }
    };

    xhr.open('POST', 'Appointments2?');
    xhr.send();


}

/* bar chart */
var chart = null;
function drawChart(xValues, yValues) {

    document.getElementById("myChart").style.display = "block";

    if (chart !== null)
        chart.destroy();

    var barColors = ["red", "green","blue"];

    chart = new Chart("myChart", {
        type: "bar",
        data: {
            labels: xValues,
            datasets: [{
                    backgroundColor: barColors,
                    data: yValues
                }]
        },
        options: {
            scales: {
                yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
            },
            legend: {display: false},
            title: {
                display: true,
                text: "Stats"
            }
            
                    
        }
    });
    
}



