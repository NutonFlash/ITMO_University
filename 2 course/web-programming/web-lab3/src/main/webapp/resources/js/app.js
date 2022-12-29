$(function () {
    let y;
    let x;

    let cordX;
    let cordY;
    let inR = "form:coord-r_label",
        inY = "form:coord-y";
//------------------------------Обработка нажатия на картинку----------------------------------------
    document.getElementById("area-zone").addEventListener("click", function (e) {
        cordX = e.offsetX;
        cordY = e.offsetY;
        if (isRValid()) {
            detectClick();
        }
    })

    document.getElementById(inY).addEventListener("change", handleYInput);

    function handleYInput() {
        let domY = document.getElementById(inY);
        domY.value = domY.value.replace(",", ".");
    }

    function detectClick() {
        convertCoordinates();
        let x_hidden = 'hidden-form:hidden-x-input';
        let y_hidden = 'hidden-form:hidden-y-input';
        let sub_btn_hidden = 'hidden-form:hidden-submit-button';
        document.getElementById(x_hidden).setAttribute('value',x);
        document.getElementById(y_hidden).setAttribute('value',y);
        document.getElementById(sub_btn_hidden).click();
    }

    function convertCoordinates() {
        changeXCord();
        changeYCord();
        x = convertCoordinate(cordX);
        y = convertCoordinate(cordY);
        x = String(x).replace(",", ".");
        y = String(y).replace(",", ".");
    }


    function convertCoordinate(coord) {
        let r = document.getElementById(inR).textContent;
        return (coord / 100) * r;
    }

    $('.button-input').click(function () {
        $('.button-input-selected')
            .add(this)
            .toggleClass('button-input-selected')
            .toggleClass('button-input');
        return true;
    })

    function changeXCord() {
        let centerX = 150;
        if (cordX < centerX) {
            cordX = -(centerX - cordX);
        } else {
            cordX = cordX - centerX;
        }
    }

    function changeYCord() {
        let centerY = 150;
        if (cordY > centerY) {
            cordY = -(cordY - centerY);
        } else {
            cordY = centerY - cordY;
        }
    }

//--------------------------------------Валидация полей формы-----------------------------------
    function isRValid() {
        let r = document.getElementById(inR).textContent;
        return isNumber(r) && ((r <= 4) && r >= 1);
    }

    function isXValid() {
        return document.getElementsByClassName('button-input-selected').length > 0;
    }

    function isYValid(){
        let y = document.getElementById(inY).value;
        return isNumber(y) && (y<=5 && y>=-5);
    }

    function isNumber(n) {
        return !isNaN(parseFloat(n)) && !isNaN(n - 0)
    }
//-----------------------------------Формирование сообщения об ошибке------------------------------
    function sendErrorMsgForX() {
        let xInput = document.getElementById('x-error');
        xInput.innerHTML = "<p>*Please choose X value</p>";
        xInput.lastChild.style = "color: #FF4500;" +
            "padding-left: 20px;" + "font-family: 'Montserrat', sans-serif;";
    }
    function sendErrorMsgForY() {
        let yInput = document.getElementById('y-error');
        yInput.innerHTML = "<p>*Please write correct Y value</p>";
        yInput.lastChild.style = "color: #FF4500;" +
            "padding-left: 20px;" + "font-family: 'Montserrat', sans-serif;";
    }

    function clearErrorMsgForX(){
        let xInput = document.getElementById('x-error');
        xInput.innerHTML = "";
    }
    function clearErrorMsgForY(){
        let yInput = document.getElementById('y-error');
        yInput.innerHTML = "";
    }
//--------------------------------------Обработка нажатия на Submit-----------------------------------
    let submitButton = document.getElementById('submitParent').childNodes[0];
    submitButton.addEventListener('click', function (event) {
       if (!(isRValid() && isXValid() && isYValid())) {
           console.log("данные не прошли проверку");
           event.preventDefault();
           if (!isXValid())
               sendErrorMsgForX();
           else clearErrorMsgForX();
           if (!isYValid())
               sendErrorMsgForY();
           else clearErrorMsgForY();
           return false;
       } else {
           console.log("данные прошли проверку");
           clearErrorMsgForX();
           clearErrorMsgForY()
           return true;
       }
    });
});

