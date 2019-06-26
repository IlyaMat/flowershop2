
    function validformlogin() {
     var username = document.forms["my-form"]["username"].value;
     var pass = document.forms["my-form"]["password"].value;


            if (username==null || username=="")
            {
                alert("Введите ваш логин");
                return false;
            }else if (pass==null || pass=="")
            {
                alert("Введите ваш пароль");
                return false;
            }


        }

/*$(document).ready(function () {

    var $login = $('[role=login]');

     $login.each(function () {
           var username = document.forms["my-form"]["username"].value;

            //var quantity = $(this).attr('data-atr');

            $(this).on('click', function () {
                $.ajax({
                   type: "POST",
                   url: '/disp/user/login',
                   contentType: "application/json; charset=utf-8",
                   data: JSON.stringify({username:username}),
                   dataType: "json",

                }).done(function (data) {
                    refreshCartFields.call(this, data);
                })
            });
        })


});*/






