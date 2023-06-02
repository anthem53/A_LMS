var admin = {

    init : function(){

    },
    getUserInfo : function(user_id){
        console.log("[debug] call getUserInfo");


        var url = "/api/v1/admin/userInfo/"+user_id;
        $.ajax({
             type: 'POST',
             url: url,
             contentType:'application/json; charset=utf-8',
        }).done(function(result) {
             console.log(result);

             $("#staticId").val(result.id)
             $("#staticName").val(result.name)
             $("#staticEmail").val(result.email)
             $("#staticRole").val(result.role)
             $("#staticLectureOffering").val(result.lecture_offering)
             $("#staticLectureAttended").val(result.lecture_submit)



        }).fail(function (error) {
             alert(JSON.stringify(error));
        });

    }

}

admin.init()
