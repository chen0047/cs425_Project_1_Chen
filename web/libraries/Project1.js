var Project1 = (function () {

    return {

        init: function () {

            $("#version").html("jQuery Version:" + $().jQuery);

        },
        
        nameChange: function () {
            
            var first = $("#firstname").val();
            var last = $("#lastname").val();

            $("#displayname").val(first + " " + last);

        },

        submitSessionForm: function () {

            $.ajax({

                url: 'registrations',
                method: 'GET',
                data: $('#searchform').serialize(),
                success: function (response) {
                    
                    $("#resultarea").html(response);

                }


            });

            return false;
        },
        
        submitNewRegister: function(){
            
            $.ajax({
               
                url: 'registrations',
                method: 'POST',
                data: $("#register").serialize(),
                
                success: function(response){
                    
                    var code = response["code"];
                    var displayname = response["displayname"];
                    
                    var output = "<br/>Congratulation! You have successfully \n\
registered as : " + displayname + "<p>Your registration code is:<b> " + code + "</b></p>";
                    
                    $("#resultarea").html(output);
                    
                }
                
            });
            
            return false;
        }
    }
    
}());



