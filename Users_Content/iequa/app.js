//Глобальные поля приложения

//Константы выигрышных вариантов
const FROM_LEFT_TOP_CORNER_TO_RIGHT_BOT = [0, 4, 8];
const FROM_RIGHT_TOP_CORNER_TO_LEFT_BOT = [2, 4, 6];
const TOP_LINE_HORIZONTAL = [0, 1, 2];
const MIDDLE_LINE_HORIZONTAL = [3, 4, 5];
const BOT_LINE_HORIZONTAL = [6, 7, 8];
const LEFT_LINE_VERTICAL = [0, 3, 6];
const MIDDLE_LINE_VERTICAL = [1, 4, 7];
const RIGHT_LINE_VERTICAL = [2, 5, 8];
////////////////

//Игровая матрица значений
let fieldsValues = 
[   
    0,0,0,
    0,0,0,
    0,0,0
];
////////////////
//Количество свободных для записи полей.
let availableFields = 9;
////////////////

//Сторона, которая делает ход в текущий момент (true - X, false - o)
let mainPlayerTurn = true;
////////////////

let someoneWin = false;

//Счёт
let scoreX = 0;
let scoreO = 0;
////////////////

//Тип противника (человек - true, компьютер - false)
let pvpMode = true;
////////////////

//Шаблон для отрисовки крестика
const X_ELEMENT = "<div class=\"x\"></div>";
////////////////

//Шаблон для отрисовки нолика
const O_ELEMENT = "<div class=\"o\"></div>";
////////////////

//Задаём обработчики событий для элементов
start_button.onclick = buttonClickHandler;
allDivFields.onclick = divClickHandler;
pvp.onclick = gamemodeClickHandler;
pve.onclick = gamemodeClickHandler;
////////////////

//Основной обработчик. Обрабатывает клик по игровому полю.
function divClickHandler(event) {
    
    //Проверяем класс кликнутого объекта, если это <divs_top*> то обрабатываем событие.
    if (!someoneWin && event.srcElement.className !== "x" && event.srcElement.className !== "o") {
        //Выбираем позицию установки
        const id = selectOrComputePlaceId(pvpMode, mainPlayerTurn) ? event.srcElement.id : computeIdAndPlace(fieldsValues);
        //Получаем выбранный див
        let currentDiv = document.getElementById(id);
        //Отрисовываем объект
        currentDiv.innerHTML = mainPlayerTurn ? X_ELEMENT : O_ELEMENT;
        //Берём последний символ из id выбранного объекта, выставляем значение в массив
        const fieldIndex = parseInt(currentDiv.id.charAt(currentDiv.id.length-1));
        fieldsValues[fieldIndex-1] = mainPlayerTurn ? 1 : 2;

        //Проверяем поле на победу одной из сторон
        someoneWin = checkFieldsForWinner(fieldsValues, mainPlayerTurn);

        //Ход другого игрока
        mainPlayerTurn = !mainPlayerTurn;

        //Уменьшаем количество доступных полей
        availableFields--;
    }
    //Автоход компьютера
    if (!someoneWin && !pvpMode && !mainPlayerTurn && availableFields>0) {
        divClickHandler(event);
    }
}

//Обработчик кнопки сброса состояния игры
function buttonClickHandler() {
    availableFields = 9;
    fieldsValues = [0,0,0,0,0,0,0,0,0];
    mainPlayerTurn = true;
    for(let i = 1; i<=fieldsValues.length;i++) {
        const elem = document.getElementById("divs_top"+i);
        elem.innerHTML = "";
    }
    document.getElementById("board").innerHTML = `\"Крестики\"|${scoreX}:${scoreO}|\"Нолики\"`;
    someoneWin = false;
};

//Обработчик режима игры
function gamemodeClickHandler(event) {
    pvpMode = event.currentTarget.value === "true";
}

//Вспомогательная функция выбора ID для див'а
function selectOrComputePlaceId(pvp, mainPlayerTurn) {
    if (!pvp) {
        if (!mainPlayerTurn) {
            return false;
        } else {
            return true;
        }    
    }
    return true;
}

//Рандомные ходы для компьютера
function computeIdAndPlace(fieldsValues) {
    let place = 4;
    while (fieldsValues[place] !== 0) {
        place = getRandomInt(8);
    }
    return `divs_top${place+1}`;
}

//Проверка игрового поля на предмет выигрыша одной из сторон
function checkFieldsForWinner(fieldsValues, lastPlayer) {
    const lastPlayerIndex = lastPlayer ? 1 : 2;
    if(
        arrayCheck(fieldsValues, FROM_LEFT_TOP_CORNER_TO_RIGHT_BOT, lastPlayerIndex) ||
        arrayCheck(fieldsValues, FROM_RIGHT_TOP_CORNER_TO_LEFT_BOT, lastPlayerIndex) ||
        arrayCheck(fieldsValues, TOP_LINE_HORIZONTAL, lastPlayerIndex) ||
        arrayCheck(fieldsValues, MIDDLE_LINE_HORIZONTAL, lastPlayerIndex) ||
        arrayCheck(fieldsValues, BOT_LINE_HORIZONTAL, lastPlayerIndex) ||
        arrayCheck(fieldsValues, LEFT_LINE_VERTICAL, lastPlayerIndex) ||
        arrayCheck(fieldsValues, MIDDLE_LINE_VERTICAL, lastPlayerIndex) ||
        arrayCheck(fieldsValues, RIGHT_LINE_VERTICAL, lastPlayerIndex) 
    )   {
            //Отрисовываем победное окно в окне с результатами
            let baseDiv = document.getElementById("board");
            const winner = mainPlayerTurn ? "Крестик" : "Нолик";
            mainPlayerTurn ? scoreX++ : scoreO++;
            baseDiv.innerHTML = `\"Крестики\"|${scoreX}:${scoreO}|\"Нолики\"` + `<p class="win_text">${winner} победил! </p>`;
            return true;
    }
    return false;
}

//Вспомогательный метод для проверки выигрышного расположения одной из сторон
function arrayCheck(fieldsValues, indexes, lastPlayerIndex) {
    if( fieldsValues[indexes[0]] === lastPlayerIndex && 
        fieldsValues[indexes[1]] === lastPlayerIndex && 
        fieldsValues[indexes[2]] === lastPlayerIndex
    ) {
        return true;
    }
    return false;
}

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
  }