// find myCanvas in HTML document
var canvas = document.getElementById("myCanvas");

// get a context object providing methods to draw
var context = canvas.getContext("2d");

// if a click event occurs to the canvas, call defineImage function
canvas.addEventListener("click", defineImage, false);


// On event, get the position in the canvas of the mouse
function getCurrentPos(evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

// Determines the selected color, shape and location of the mouse click.
// Creates a JSON representation. Draws that and sends it to the server.

function defineImage(evt) {

    // where did the click occur
    var currentPos = getCurrentPos(evt);

    // find the color checked
    for (i = 0; i < document.inputForm.color.length; i++) {
        if (document.inputForm.color[i].checked) {
            var color = document.inputForm.color[i];
            break;
        }
    }

    // find the shape checked
    for (i = 0; i < document.inputForm.shape.length; i++) {
        if (document.inputForm.shape[i].checked) {
            var shape = document.inputForm.shape[i];
            break;
        }
    }

    // create a string from a json object from shape and color and position
    var json = JSON.stringify({
        "shape": shape.value,
        "color": color.value,
        "coords": {
            "x": currentPos.x,
            "y": currentPos.y
        }
    });
    // draw it here locally
    drawImageText(json);
    // draw it everywhere
    // send a json string to the server
    sendText(json);
}


// Takes a json string and draws it
function drawImageText(image) {
    console.log("drawImageText");
    var json = JSON.parse(image);
    context.fillStyle = json.color;
    switch (json.shape) {
        case "circle":
            context.beginPath();
            context.arc(json.coords.x, json.coords.y, 5, 0, 2 * Math.PI, false);
            context.fill();
            break;
        case "square":
        default:
            context.fillRect(json.coords.x, json.coords.y, 10, 10);
            break;
    }
}

