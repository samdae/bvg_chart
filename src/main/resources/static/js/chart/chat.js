// // Get the modal
// // var modal = document.getElementById('myModal');
//
// // Get the button that opens the modal
// // var btn = document.getElementById("myBtn");
//
// // Get the <span> element that closes the modal
// var span = document.getElementsByClassName("close")[0];
//
// // When the user clicks on the button, open the modal
// btn.onclick = function () {
//
//     if ($("#name").val() == "") {
//         modal.style.display = "block";
//     } else {
//         if ($(".chat-area").css("display") == "block") {
//             $(".chat-area").css("display", "none");
//
//         } else {
//             $(".chat-area").css("display", "block");
//         }
//     }
//
// }
//
// $("#connect").click(function () {
//     test();
// });
// // When the user clicks on <span> (x), close the modal
// span.onclick = function () {
//     modal.style.display = "none";
// }
//
// // When the user clicks anywhere outside of the modal, close it
// window.onclick = function (event) {
//     if (event.target == modal) {
//         modal.style.display = "none";
//     }
// }
//
// function test() {
//     var name = $("#name").val();
//     if (name == '') {
//         alert('대화명을 입력하세요!');
//         return;
//     }else{
//         if(name.length > 5 || name.length < 2){
//             alert('대화명은 2~5자로 지정해주세요.');
//             return;
//         }
//     }
//     document.getElementById('iframe').contentWindow.connect(name);
//     $("#myModal").css("display", "none");
//     $(".chat-area").css("display", "block");
// }
//
// function closeChatting() {
//     $(".chat-area").css("display", "none");
//     $("#name").val('');
//
//
// }
//
//
//
// //////////////////////
//
