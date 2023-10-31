/* show password */
function show_pass() {
    var show = document.getElementById("password");
    if (show.type === "password") {
        show.type = "text";
    } else {
        show.type = "password";
    }
}

/* show password confirmation*/
function show_conf() {
    var show = document.getElementById("conf");
    if (show.type === "password") {
        show.type = "text";
    } else {
        show.type = "password";
    }
}

/* check if passwords match*/
function checkPassword() {
    var password = document.getElementById("password").value;
    var confirmation = document.getElementById("conf").value;

    if (password !== confirmation) {
        document.getElementById("alert").innerHTML = "Passwords do not match!";
        document.getElementById("submit").disabled = true;
        return false;
    } else if (password === confirmation) {
        document.getElementById("alert").innerHTML = "Passwords match!";
        document.getElementById("submit").disabled = false;
        return true;
    }
}

/* check password strength */
var strong;

function passwordStrength() {

    var password = document.getElementById("password").value;

    var flag = 0;

    var upperCaseLetters = /[A-Z]/;
    var lowerCaseLetters = /[a-z]/;
    var numbers = /[0-9]/;
    var special = /.*[!,%,&,@@,#,$,^,*,?,_,~,+,=,`,(,),|,\,.,/,{,},:,;,",',[\]]/;

    var lrepeat = /([a-zA-Z]).*?\1\1/;
    var lresult = lrepeat.test(password);


    if (password.length < 8) {
        document.getElementById("pass_check").innerHTML = "Weak Password - at least 8 characters";
        flag = 1;
    }
    if (password.length >= 8) {
        if (!password.match(lowerCaseLetters) && !password.match(upperCaseLetters)) {
            document.getElementById("pass_check").innerHTML = "Weak Password - no letter";
            flag = 1;
        }
        if (!password.match(special)) {
            document.getElementById("pass_check").innerHTML = "Weak Password - no special character";
            flag = 1;
        }
        if (!password.match(numbers)) {
            document.getElementById("pass_check").innerHTML = "Weak Password - no number";
            flag = 1;
        }

    }

    if (lresult) {
        document.getElementById("pass_check").innerHTML = "Weak Password - repeated letter";
        flag = 1;
    }

    if (number_count(password) >= 4) {
        document.getElementById("pass_check").innerHTML = "Weak Password - too many numbers";
        flag = 1;
    }

    if (password.length === 8) {
        if (is_strong(password)) {
            document.getElementById("pass_check").innerHTML = "Strong Password";
            strong = 1;
        }
        if (flag === 0 && strong !== 1) {
            document.getElementById("pass_check").innerHTML = "Medium Password";
        }
        strong = 0;
    }

}

/* count numbers in input */
function number_count(n) {
    return Math.max(Math.floor(Math.log10(Math.abs(n))), 0) + 1;
}

/* check if all characters in password are different */
function is_strong(password) {
    var pass = {};
    var i;
    var char;
    for (i = 0; i < password.length; i++) {
        char = password[i];
        if (pass[char])
            return false;
        pass[char] = true;
    }
    return true;
}


/* show or hide extra fields for doctor */
function doctor() {
    if (document.getElementById('doc').checked) {
        $('.user').hide();
        $('.doctor').show();
    } else {
        $('.user').show();
        $('.doctor').hide();
    }
}

/* check social security number */
function ssn() {
    let birthday = document.getElementById("birthdate").value.toString();
    var year = birthday.substring(2, 4);
    var month = birthday.substring(5, 7);
    var day = birthday.substring(8, 10);

    let amka = document.getElementById("amka").value.toString();
    var day2 = amka.substring(0, 2);
    var month2 = amka.substring(2, 4);
    var year2 = amka.substring(4, 6);


    if ((day.localeCompare(day2) === 0) && (month.localeCompare(month2) === 0) && (year.localeCompare(year2) === 0)) {
        document.getElementById("alert2").innerHTML = "SNN is correct!";
    } else {
        document.getElementById("alert2").innerHTML = "SNN is not correct!";
    }
}

/*check if username contains the word admin*/
function usernameCheck() {
    var username = document.getElementById("username").value;

    if (username.indexOf('admin') > -1)
    {
        document.getElementById("username_check").innerHTML = "Username should not contain the word 'admin'.";
    }
}

/* check if user or doctor exist in database */
function check_exist() {

    if (document.getElementById("user").checked) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {

                $("#ajaxContent").html("<b>Registration successful</b><br>");
                postUser();

                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 10000);

            } else if (xhr.status === 405) {
                $("#ajaxContent").html("Full name does not exist in database.");
            } else if (xhr.status === 403) {
                $("#ajaxContent").html("Usernname taken");
            } else if (xhr.status === 406) {
                $("#ajaxContent").html("Email taken");
            } else {
                $("#ajaxContent").html("SSN taken");
            }
        };
        var data = $('#Form').serialize();
        xhr.open('GET', 'GetUser?' + data);
        xhr.send();
    } else if (document.getElementById("doc").checked) {

        var xhr2 = new XMLHttpRequest();
        xhr2.onload = function () {
            if (xhr2.readyState === 4 && xhr2.status === 200) {
                $("#ajaxContent").html("<b>Registration successful but you need to be verified by an admin</b><br>");
                postUser();

                setTimeout(function () {
                    window.location.href = 'login.html';
                }, 10000);

            } else if (xhr2.status === 403) {
                $("#ajaxContent").html("Usernname taken");
            } else if (xhr2.status === 406) {
                $("#ajaxContent").html("Email taken");
            } else {
                $("#ajaxContent").html("There was a problem.");
            }
        };
        var data = $('#Form').serialize();
        xhr2.open('GET', 'GetDoctor?' + data);
        xhr2.send();
    }


}

/* add user or doctor in database */
function postUser() {
    let myForm = document.getElementById('Form');
    let formData = new FormData(myForm);
    const data = {};

    formData.forEach((value, key) => {
        if (value !== "")
            data[key] = value;
    });

    for (var pair of formData.entries()) {
        if (pair[1] !== "")
            document.getElementById("ajaxContent").innerHTML += "<br><b>" + pair[0] + "</b>=" + pair[1];
    }

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status !== 200) {
            console.log(xhr.status);
        }
    };
    if (document.getElementById("user").checked) {
        xhr.open('POST', 'GetUser');
    } else {
        xhr.open('POST', 'GetDoctor');
    }
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(data));
}

/* get session */
function isLoggedIn() {
    var xhr = new XMLHttpRequest();

    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#account").load("account.html");
            show_data();
        }
    };
    xhr.open('GET', 'Login');
    xhr.send();
}
$(document).ready(function () {
    isLoggedIn();
});