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
             console.log(typeof(result.lecture_offering))

             if (result.lecture_offering.length == 0){
                $("#staticLectureOffering").val("No Lecture")
             }
             else{
                $("#staticLectureOffering").val(result.lecture_offering)
             }
             if (result.lecture_submit.length == 0){
                 $("#staticLectureAttended").val("No Lecture")
              }
              else{
                 $("#staticLectureAttended").val(result.lecture_submit)
              }




        }).fail(function (error) {
             alert(JSON.stringify(error));
        });

    },
    getLectureInfo : function(lecture_id){
        console.log(lecture_id)
        var url = "/api/v1/admin/lectureInfo/"+lecture_id;
        $.ajax({
             type: 'POST',
             url: url,
             contentType:'application/json; charset=utf-8',
        }).done(function(result) {
             console.log(result);

             $("#staticLecturerId").val(result.lecturer_id)
             $("#staticId").val(result.id)
             $("#staticLecturer").val(result.lecturer)
             $("#staticTitle").val(result.title)
             $("#staticOutline").val(result.outline)
             $("#staticAttendeeNum").val(result.attendeeNum)




        }).fail(function (error) {
             alert(JSON.stringify(error));
        });

    },
    getReportInfo : function(report_id){
        console.log(report_id)
        var url = "/api/v1/admin/reportInfo/"+report_id;
        $.ajax({
             type: 'POST',
             url: url,
             contentType:'application/json; charset=utf-8',
        }).done(function(result) {
             console.log(result);

             $("#staticId").val(result.id)
             $("#staticReporterName").val(result.reporterName)
             $("#staticReporterId").val(result.reporterId)
             $("#staticLink").val(result.link)
             $("#staticLink").on("click",function(){
                alert("신고받은 페이지로 이동합니다.")
                window.location.href = result.link
             });
             $("#staticContent").val(result.content)



        }).fail(function (error) {
             alert(JSON.stringify(error));
        });


    }

}

admin.init()
