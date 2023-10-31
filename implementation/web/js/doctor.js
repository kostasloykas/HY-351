let doctor_id;
let hospital_id;

function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];

        if (category === "doctor_id")
            doctor_id = value;
        if (category === "hospital_id")
            hospital_id = value;

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
    xhr.open('POST', 'Doctors');
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
            $("#account").load("doctor.html");
            show_data();
        }
    };
    xhr.open('GET', 'Login');
    xhr.send();
}
$(document).ready(function () {
    isLoggedIn();
    ShowAccountTab();
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

/* update doctor data */
function update_data_doctor() {
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

/* check if nurse exist in database */
function check_nurse_exist() {

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            postNurse();

        } else if (xhr.status === 403) {
            $("#nurse_register_alert").html("Usernname taken");
        } else if (xhr.status === 406) {
            $("#nurse_register_alert").html("Email taken");
        } else {
            $("#nurse_register_alert").html("There was a problem.");
        }
    };
    var data = $('#Form').serialize();
    xhr.open('GET', 'GetNurse?' + data);
    xhr.send();

}

/* add nurse in database */
function postNurse() {
    let myForm = document.getElementById('nurse_form');
    let formData = new FormData(myForm);
    const data = {};

    formData.append('doctor_id', doctor_id);
    formData.append('hospital_id', hospital_id);

    formData.forEach((value, key) => {
        if (value !== "")
            data[key] = value;
    });

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#nurse_register_alert").html("Nurse successfully added.");
        } else if (xhr.status !== 200) {
            $("#nurse_register_alert").html("There was a problem.");
        }
    };

    xhr.open('POST', 'GetNurse');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(data));
}



//show account tab
function ShowAccountTab() {
    $("#appointment-tab").hide();
    $("#account-tab").show();
    $("#new-appointment-tab").hide();
    $("#nurse_form").hide();
}



//show appointment tab
function ShowAppointmentTab() {
    $("#account-tab").hide();
    $("#appointment-tab").show();
    $("#new-appointment-tab").hide();
    $("#nurse_form").hide();
    document.getElementById("appointment-table").innerHTML = "";

    GetAppointments();

}

//show add appointment tab
function ShowNewAppointmentTab() {
    $("#account-tab").hide();
    $("#appointment-tab").hide();
    $("#new-appointment-tab").show();
    $("#nurse_form").hide();
    GetAppointments();

}


//pairnei ola ta randevou pou exei o sugkekrimenos giatros kai
//arxikopoiei ton pinaka me ta stoixeia kai thn wra pou exei kleisei o user
function GetAppointments() {

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            if (data.length === 0)
                return;
            let table = document.createElement('table');
            let firstname = document.createElement('th');
            let lastname = document.createElement('th');
            let email = document.createElement('th');
            let date = document.createElement('th');
            let vaccine_done = document.createElement('th');

            email.innerHTML = "Email";
            firstname.innerHTML = "First Name";
            lastname.innerHTML = "Last Name";
            vaccine_done.innerHTML = "Vaccine Done";
            date.innerHTML = "Date";

            let row = document.createElement('tr');
            row.appendChild(firstname);
            row.appendChild(lastname);
            row.appendChild(email);
            row.appendChild(date);
            row.appendChild(vaccine_done);
            table.appendChild(row);


            for (x in data) {
                firstname = document.createElement('td');
                lastname = document.createElement('td');
                email = document.createElement('td');
                date = document.createElement('td');
                vaccine_done = document.createElement('td');

                email.innerHTML = data[x].email;
                firstname.innerHTML = data[x].firstname;
                lastname.innerHTML = data[x].lastname;
                date.innerHTML = data[x].date_time;
                vaccine_done.innerHTML = "<button type='button' onclick = 'VaccineDone(" + "\"" + data[x].email + "\"" + ",\"" + data[x].randevou_id + "\"" + ",\"" + data[x].vaccine + "\"" + "); '>Done!</button>";

                row = document.createElement('tr');
                row.id = email.innerHTML;
                row.appendChild(firstname);
                row.appendChild(lastname);
                row.appendChild(email);
                row.appendChild(date);
                row.appendChild(vaccine_done);

                table.appendChild(row);
            }

            document.getElementById('appointment-table').appendChild(table);


        } else if (xhr.status !== 200) {
            alert("Error: GetAppointments");
        }
    };

    let data = JSON.stringify({"id_doctor": doctor_id.toString()});
    xhr.open('POST', 'GetAppointments');
    xhr.send(data);
}


// enhmerwnei thn bash oti o emvoliasmos egine ston sugkekrimeno user
function VaccineDone(email, randevou_id, vaccine) {

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const element = document.getElementById(email);
            alert("Update Completed Vaccine Done");
            element.remove();
        } else if (xhr.status !== 200) {
            alert("Error: VaccineDone");
        }
    };

    let data = JSON.stringify({"email": email.toString(), "randevou_id": randevou_id.toString(), "vaccine": vaccine.toString()});
    xhr.open('POST', 'VaccineDone');
    xhr.send(data);
}

/* add new appointment */
function add_new_appointment() {

    var date_time = document.getElementById("date_time").value;
    var vaccine = document.getElementById("vaccine").value;

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#appointment_add_alert").html("Appointment successfully added.");
        } else if (xhr.status !== 200) {
            $("#appointment_add_alert").html("There was a problem.");
        }
    };
    /* GetUncertifiedDoctors had an empty POST method */
    xhr.open("POST", "GetUncertifiedDoctors?doctor_id=" + doctor_id + "&date_time=" + date_time + "&hospital_id=" + hospital_id + "&vaccine=" + vaccine);
    xhr.send();

}

/* show nurse tab */
function ShowNurseTab() {
    $("#account-tab").hide();
    $("#appointment-tab").hide();
    $("#nurse_form").show();
}

/* change active tab */
$("li").click(function () {
    if (!$(this).hasClass("active")) {
        $("li.active").removeClass("active");
        $(this).addClass("active");
    }
});


