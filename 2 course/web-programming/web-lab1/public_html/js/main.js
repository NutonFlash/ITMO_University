window.addEventListener('load',()=>{

    let buttons = document.querySelectorAll('.r-button,.r-clickedButton');
    for(let i=0;i<buttons.length;i++) {
        buttons[i].addEventListener('click',clickHandle);
    }
    function clickHandle(){
        for(let i=0;i<buttons.length;i++) {
            if(this.id.split('r-button')[1]-1===i) {
                if (this.className === 'r-button')
                    this.classList.replace('r-button','r-clickedButton');
                else this.classList.replace('r-clickedButton','r-button');
            } else {
                if(buttons[i].className==='r-clickedButton')
                    buttons[i].classList.replace('r-clickedButton','r-button');
            }
        }
    }
});
function submitForm() {

    //Validation triggers
    let X_valid = false;
    let R_valid = false;
    let Y_valid = false;

    //Values of fields
    let Y_val, R_val;
    let X_val = [];

    let X_select = document.querySelector('select');
    let Y_text = document.getElementById('y-textinput');
    let R_button = document.querySelector('.r-clickedButton');

    //R validation
    if (R_button!==null){
        R_val = R_button.textContent;
        R_valid = true;
    }

    //Y validation
    Y_val = Y_text.value;
    let matchResult = Y_val.match(/^-?[0-5](\.\d+)?/g);
    for (let elem of matchResult) {
        if (elem === Y_val)
            Y_valid = true;        
    }


    //X validation
    if (X_select.value !== 'Select') {
        X_valid = true;
        X_val = X_select.value;
    }

    if (!(X_valid && Y_valid && R_valid)) {
        if (!R_valid) {
            document.getElementById("r").style = "color: #FF4500;"
            let rErr = document.getElementById("r_err");
            rErr.innerHTML = "*Please choose R value";
            rErr.style = "color: #FF4500;" +
                "padding-bottom: 10px";
        } else {
            document.getElementById("r").style = "color: #FFFFFF;";
            document.getElementById("r_err").innerHTML = "";
        }

        if (!Y_valid) {
            document.getElementById("y").style = "color: #FF4500;";
            let yErr = document.getElementById("y_err");
            yErr.innerHTML = "*Please input correct Y value";
            yErr.style = "color: #FF4500;" +
                "padding-bottom: 10px";
            document.getElementById("y-textinput").style = "color: #FF4500;";
        } else {
            document.getElementById("y").style = "color: #FFFFFF;";
            document.getElementById("y_err").innerHTML = "";
            document.getElementById("y-textinput").style = "color: #3e3e3e;";
        }

        if (!X_valid) {
            document.getElementById("x").style = "color: #FF4500;";
            let xErr = document.getElementById("x_err");
            xErr.innerHTML = "*Please choose X value";
            xErr.style = "color: #FF4500;" +
                "padding-bottom: 10px";
        } else {
            document.getElementById("x").style = "color: #FFFFFF;";
            document.getElementById("x_err").innerHTML = "";
        }
    } else {
        function successMessage() {
            document.getElementById("x_err").innerHTML = "Форма успешно отправлена!";
            document.getElementById("x_err").style = "font-size: 16px; font-family: 'Montserrat', sans-serif; padding-bottom: 14px; font-style: italic";
        }

        function clearMessage() {
            document.getElementById("x_err").innerHTML = "";
            document.getElementById("x_err").style = "";
        }

        setTimeout(successMessage, 500);
        setTimeout(clearMessage, 3500);
        postPHP(X_val, Y_val, R_val);
        document.getElementById("x").style = "color: #FFFFFF;";
        document.getElementById("y").style = "color: #FFFFFF;";
        document.getElementById("y-textinput").style = "color: #3e3e3e;";
        document.getElementById("r").style = "color: #FFFFFF;";

        document.getElementById("x_err").innerHTML = "";
        document.getElementById("y_err").innerHTML = "";
        document.getElementById("r_err").innerHTML = "";
    }
    return false;
}

function resetForm() {
    let buttons = document.querySelectorAll('.r-button,.r-clickedButton');
    for (let i=0;i<buttons.length;i++)
        if(buttons[i].className==='r-clickedButton')
            buttons[i].classList.replace('r-clickedButton','r-button');

    document.getElementById("x").style = "color: #FFFFFF;";
    document.getElementById("y").style = "color: #FFFFFF;";
    document.getElementById("y-textinput").style = "color: #3e3e3e;";
    document.getElementById("r").style = "color: #FFFFFF;";

    document.getElementById("x_err").innerHTML = "";
    document.getElementById("y_err").innerHTML = "";
    document.getElementById("r_err").innerHTML = "";
    
    let tableLines = document.querySelectorAll('#result-table > tbody:not(.table-body)');
    Array.from(tableLines).forEach(line => {
            line.innerHTML = '';
    })

}

function postPHP(x, y, r) {
    let request = new XMLHttpRequest();
    const url = "/~s309617/main.php";
    request.open('POST', url);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.addEventListener("readystatechange", () => {
        if (request.readyState === 4 && request.status === 200) {
            let data = JSON.parse(request.responseText);
            if (Number(data.success) === 1) 
                document.getElementById("result-table").innerHTML += row(data.x, data.y, data.r, data.current, data.execution, data.result);
            else alert("Были отправлены некорректные данные, попробуйте еще раз");
        }
    });
    request.send("x=" + x + "&y=" + y + "&r=" + r + "&date=" + new Date().getTimezoneOffset());
}

function row(x, y, r, cur_time, ex_time, res) {
    return "<tr class=\"table-body\">" + "<td class =\"coords-col\">" + x + "</td>" + "<td class=\"coords-col\">" + y + "</td>" + "<td class=\"coords-col\">" + r + "</td>" + "<td class=\"time-col\">" + cur_time + "</td>" + "<td class=\"time-col\">" + ex_time + "</td>" + "<td id=\"" + res + "\">" + res + "</td>" + "</tr>"
}

