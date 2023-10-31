
$('#users-data-div').hide();
$('#new-user').hide();
$('#doctors-table').hide();
$('#new-hospital').hide();
$('#messages-div').hide();

var id_admin;


function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];

        if (category === "admin_id") {
            id_admin = value;
        }

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
    xhr.open('POST', 'Admins');
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
            $("#account").load("admin.html");
            show_data();
        }
    };
    xhr.open('GET', 'Login');
    xhr.send();
}
$(document).ready(function () {
    isLoggedIn();
});



// show account tab
function Account() {
    $('#doctors-table').hide();
    $('#users-data-div').hide();
    $('#new-user').hide();
    $('#new-hospital').hide();
    $('#messages-div').hide();
    $('#Form').show();
}


// o admin kanei certify kapooin giatro sumfwna me to email tou
function Certify(email) {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var element = document.getElementById(email);
            element.parentNode.removeChild(element);

        } else {
            alert("Error: Certify Doctor");
        }
    };
    let data = JSON.stringify({"email": email});
    xhr.open('POST', 'CertifyDoctor');
    xhr.send(data);
}


//arixkopoiei ton pinaka me tous giatrous pou den exoyn ginei certify
function InitializeTable() {
    let table = document.createElement('table');
    let firstname = document.createElement('th');
    let lastname = document.createElement('th');
    let email = document.createElement('th');
    let certify = document.createElement('th');

    email.innerHTML = "Email";
    firstname.innerHTML = "First Name";
    lastname.innerHTML = "Last Name";
    certify.innerHTML = "Certify";

    let row = document.createElement('tr');
    row.appendChild(firstname);
    row.appendChild(lastname);
    row.appendChild(email);
    row.appendChild(certify);
    table.appendChild(row);


    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            if (data.length === 0) {
                $('#doctors-table').hide();
                return;
            }
            for (x in data) {
                firstname = document.createElement('td');
                lastname = document.createElement('td');
                email = document.createElement('td');
                certify = document.createElement('td');

                email.innerHTML = data[x].email;
                firstname.innerHTML = data[x].firstname;
                lastname.innerHTML = data[x].lastname;
                certify.innerHTML = "<button type='button' onclick = 'Certify(" + "\"" + data[x].email + "\"" + "); '>Certify!</button>";

                row = document.createElement('tr');
                row.id = email.innerHTML;
                row.appendChild(firstname);
                row.appendChild(lastname);
                row.appendChild(email);
                row.appendChild(certify);

                table.appendChild(row);
            }

            document.getElementById('doctors-table').appendChild(table);

        } else if (xhr.status !== 200) {
            alert("Error: GetUncertifiedDoctors");
        }
    };
    xhr.open('GET', 'GetUncertifiedDoctors');
    xhr.send();
}


// show certify tab
function CertifyDoctors() {
    $('#Form').hide();
    $('#users-data-div').hide();
    $('#new-user').hide();
    $('#new-hospital').hide();
    $('#messages-div').hide();
    $('#doctors-table').show();
    if ($('#doctors-table').children().length > 0) {
        return;
    }
    InitializeTable();

}


//show user data tab
function UsersData() {
    $('#Form').hide();
    $('#new-user').hide();
    $('#doctors-table').hide();
    $('#new-hospital').hide();
    $('#messages-div').hide();
    $('#users-data-div').show();
}


//kanei search enan user kai emfanizei ta stoixeia tou
function SearchUser(amka) {
    var xhr = new XMLHttpRequest();


    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            $('#email').val(data.email);
            $('#username').val(data.username);
            $('#lastname').val(data.lastname);
            $('#firstname').val(data.firstname);
            $('#birthdate').val(data.birthdate);
            $('#city').val(data.city);
            $('#gender').val(data.gender);
            $('#address').val(data.address);
            $('#addr_no').val(data.addr_no);
            $('#telephone').val(data.telephone);
            $('#new-amka').val(data.amka);


        } else if (xhr.status !== 200) {
            alert("Error: SearchUser");
        }
    };

    let data = JSON.stringify({"amka": amka});
    xhr.open('POST', 'GetUserData');
    xhr.send(data);
}

//pernei ta stoixeia tou user pou exoun allaxtei kai kanei update
function UpdateUser(amka) {

    let email = $('#email').val();
    let username = $('#username').val();
    let lastname = $('#lastname').val();
    let firstname = $('#firstname').val();
    let birthdate = $('#birthdate').val();
    let city = $('#city').val();
    let gender = $('#gender').val();
    let address = $('#address').val();
    let addr_no = $('#addr_no').val();
    let telephone = $('#telephone').val();
    let new_amka = $('#new-amka').val();

    var xhr = new XMLHttpRequest();


    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("User Updated Succesfully");
        } else if (xhr.status !== 200) {
            alert("Error: UpdateUser");
        }
    };

    let data = JSON.stringify({"amka": amka, "email": email, "username": username, "lastname": lastname, "firstname": firstname, "city": city, "birthdate": birthdate, "gender": gender, "address": address,
        "addr_no": addr_no, "telephone": telephone, "new_amka": new_amka});
    xhr.open('POST', 'UpdateUser');
    xhr.send(data);
}

//show new user tab
function ShowNewUserTab() {
    $('#Form').hide();
    $('#doctors-table').hide();
    $('#users-data-div').hide();
    $('#new-hospital').hide();
    $('#messages-div').hide();
    $('#new-user').show();
}


//kanei add ena neo user kai pros8etei sthn bash mono to onoma kai to epi8eto
function AddNewUser(firstname, lastname) {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("New User Added");
        } else if (xhr.status !== 200) {
            alert("Error: AddNewUSer");
        }
    };

    let data = JSON.stringify({"firstname": firstname, "lastname": lastname});
    xhr.open('POST', 'NewUser');
    xhr.send(data);

}


// show new hospital tab
function ShowNewHospitalTab() {
    $('#Form').hide();
    $('#doctors-table').hide();
    $('#users-data-div').hide();
    $('#new-user').hide();
    $('#messages-div').hide();
    $('#new-hospital').show();
}


//kanei add sthn bash ena nosokomeio
function AddNewHospital(name, address, addr_no, city) {

    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("New Hospital Added");
        } else if (xhr.status !== 200) {
            alert("Error: AddNewHospital");
        }
    };

    let data = JSON.stringify({"name": name, "address": address, "addr_no": addr_no, "city": city});
    xhr.open('POST', 'NewHospital');
    xhr.send(data);


}


//show message tab
function ShowMessagesTab() {
    $('#Form').hide();
    $('#doctors-table').hide();
    $('#users-data-div').hide();
    $('#new-user').hide();
    $('#new-hospital').hide();
    $('#messages-div').show();
    $('#messages-div').empty();
    LoadMessages();
}

//kanei load ta messages gia ton sugkekrimeno admin
function LoadMessages() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            for (x in data) {
                let div = document.createElement("div");
                div.setAttribute("class", "container-message");
                let img = document.createElement("img");
                img.setAttribute("style", "background-image:url('media/user_icon.png');");

                let message = document.createElement("p");
                message.innerHTML = data[x].message;

                let from = document.createElement("span");
                from.setAttribute("style", "float:right;");
                from.innerHTML = "From " + data[x].sender + " with id " + data[x].user_id + " at " + data[x].date_time;
                div.appendChild(img);
                div.appendChild(message);
                div.appendChild(from);

                $("#messages-div").append(div);
            }

        } else if (xhr.status !== 200) {
            alert("Error: Load Messages");
        }
    };
    xhr.open('POST', 'GetMessages');
    xhr.send();
}

/* change active tab */
$("li").click(function () {
    if (!$(this).hasClass("active")) {
        $("li.active").removeClass("active");
        $(this).addClass("active");
    }
});










