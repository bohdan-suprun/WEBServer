/**
 * Created by bod on 31.08.15.
 */

function setMethod(){
    var element = document.getElementsByClassName("method")[0];
    var myForm = document.getElementsByTagName("form")[0];
    myForm.setAttribute("method", element.value);
}
