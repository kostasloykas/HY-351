function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];

        html += "<tr><td><b>" + category + "</td><td></b>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;
}

/* show user's data */
function show_data() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#data_alert").html(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status !== 200) {
            $("#data_alert").html("Data could not be retrieved");
        }
    };
    xhr.open('POST', 'Nurses');
    xhr.send();
}

/* user logout */
function logout_func() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html("Successful Logout");
            $("#account").load("login.html");
        } else if (xhr.status !== 200) {
            $("#ajaxContent").html("Problem in Logout");
        }
    };
    xhr.open('GET', 'Logout');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

/* get session */
function isLoggedIn() {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#account").load("nurse.html");
            show_data();
        }
    };
    xhr.open('GET', 'Login');
    xhr.send();
}
$(document).ready(function () {
    isLoggedIn();
});

/* add attribures to certain fields */
function select_change() {
    var choice = document.getElementById("choice").value;
    var input = document.getElementById("change");

    if (choice === "birthdate") {
        document.getElementById('change').type = 'date';
        input.setAttribute("value", "1980-01-01");
        input.setAttribute("min", "1920-01-01");
        input.setAttribute("max", "2005-12-31");
    } else if (choice === "password") {
        document.getElementById('change').type = 'password';
        input.setAttribute("minlength", "8");
        input.setAttribute("maxlength", "15");
    } else if (choice === "blooddonor") {
        input.setAttribute("min", "0");
        input.setAttribute("max", "1");
        input.setAttribute("maxlength", "1");
    } else {
        document.getElementById('change').type = 'text';
        input.removeAttribute("value");
        input.removeAttribute("minlength");
        input.removeAttribute("maxlength");
    }
}

/* update nurse data */
function update_data_nurse() {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#update_alert").html("Data updated sucessfully");
            show_data();
        } else if (xhr.status === 409) {
            $("#update_alert").html("Email is taken");
        } else {
            $("#update_alert").html("Problem in update");
        }
    };
    var data = $('#Form').serialize();
    var change = document.getElementById("change").value;
    if (change !== "") {
        xhr.open('POST', 'Update?' + data);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xhr.send();
    } else {
        $("#update_alert").html("Box is empty");
    }

}