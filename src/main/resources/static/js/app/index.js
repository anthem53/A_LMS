var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-search').on('click' , function () {
            _this.searchPost();
        });

        $('#btn-comment-save').on('click', function (){
            _this.commentSave();
        });

        $('#btn-comment-delete').on('click', function(){
            _this.commentDelete();
        });

        $('#btn-lecture-save').on('click', function(){
            _this.lectureSave();
        });

        $('#btn-notice-save').on('click',function(){
            _this.noticeSave();
        });

        $('#btn-lecture_registration').on('click',function(){
            _this.lectureRegistration();
        });
        $('#btn-lecture-notice-save').on('click',function(){
            _this.lectureNoticeRegistration();
        });
        $('#btn-lecture-lesson-save').on('click',function(){
            _this.lectureLessonSave();
        })

        $('#btn-lecture-assignment-save').on('click',function(){
           _this.lectureAssignmentSave();
        });

        $('#btn-lecture-assignment-submit').on('click',function(){
            _this.lectureAssignmentSubmit();
        });

        $('#btn-file-delete').on('click',function(){
            _this.lectureAssignmentSubmittedFileDelete();
        });
        $('#btn-notice-update').on('click',function(){
            _this.noticeUpdate();
        });

        $('#btn-lecture-update').on('click',function(){
            _this.lectureUpdate();
        });
        $('#btn-lecture-assignment-update').on('click',function(){
           _this.lectureAssignmentUpdate();
        });
        $('#btn-lecture-lesson-update').on('click',function(){
           _this.lectureLessonUpdate();
        });

        $('#btn-lecture-leave').on('click',function(){
            _this.lectureLeave();
        });

        $('#btn-notice-delete').on("click",function(){
            _this.noticeDelete();
        });
        $('#btn-letureLesson-delete').on("click", function(){
            _this.lectureLessonDelete();
        });
        $('#btn-lecture-notice-delete').on('click',function(){
            _this.lectureNoticeDelete();
        });
        $('#btn-assignment-delete').on('click',function(){
            _this.lectureAssignmentDelete();
        });
        $('#btn-lecture-notice-update').on('click',function(){
            _this.lectureNoticeUpdate();
        });

        $('#btn-lecture-assignment-deleteAll').on('click',function(){
            _this.lectureAssignmentSubmittedFileAllDelete();
        });

        document.querySelectorAll('#btn-comment-update').forEach(function (item) {
            item.addEventListener('click', function () { // 버튼 클릭 이벤트 발생시
                const form = this.closest('form'); // btn의 가장 가까운 조상의 Element(form)를 반환 (closest)
                _this.commentUpdate(form); // 해당 form으로 업데이트 수행
            });
        });
    },
    lectureAssignmentSetscore : function (student_id, assignment_id){

        console.log("[debug] call lectureAssignmentSetscore");
        var student_id_str = String(student_id);
        var data = $("#score--"+student_id_str).val();

        console.log(data);


        var url = "/api/v1/assignment/"+String(assignment_id)+"/setScore/"+String(student_id);
        $.ajax({
             type: 'POST',
             url: url,
             dataType: 'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data)
         }).done(function() {
             alert('채점 점수가 반영되었습니다.');
             window.location.reload();
         }).fail(function (error) {
             alert(JSON.stringify(error));
         });

    },
    lectureAssignmentSubmittedFileAllDelete : function(){
        var lectureAssignment_id = $('#assignmentId').val();

        var url = "/api/v1/file-all-delete/"+lectureAssignment_id;


        $.ajax({
                     type: 'DELETE',
                     url: url,
                     contentType:'application/json; charset=utf-8',
                 }).done(function() {
                     alert('모든 제출 파일을 삭제했습니다.');
                     window.location.reload();
                 }).fail(function (error) {
                     alert(JSON.stringify(error));
                 });

    },
    lectureNoticeUpdate : function(){
        var lecture_id = $('#lectureId').val();
        var lectureNotice_id = $('#lectureNoticeId').val();

        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        }

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.content.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [내용]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }


        var redirectUrl = '/showLecture/register/take_course/'+lecture_id+'/notice/'+lectureNotice_id;
        var url = "/api/v1/lecture-notice-update/"+lectureNotice_id;
        $.ajax({
             type: 'POST',
             url: url,
             dataType: 'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data)
         }).done(function() {
             alert('강의 공지 정보가 변경되었습니다.');
             window.location.href = '/showLecture/register/take_course/'+$('#lectureId').val()+"/notice";
         }).fail(function (error) {
             alert(JSON.stringify(error));
         });

    },
    lectureAssignmentDelete : function(){
        var lecture_id = $('#lectureId').val();
        var lectureAssignment_id = $('#assignmentId').val();

        var redirectUrl = '/showLecture/register/take_course/'+lecture_id+'/assignment'
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/lecture/'+lecture_id+'/assignment/delete/'+lectureAssignment_id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('과제가 삭제되었습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    lectureNoticeDelete : function(){
        var lecture_id = $('#lectureId').val();
        var lectureNotice_id = $('#lectureNoticeId').val();

        var redirectUrl = '/showLecture/register/take_course/'+lecture_id+'/notice'
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/lecture/'+lecture_id+'/lecture_notice/delete/'+lectureNotice_id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('강의내 공지가 삭제되었습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    lectureLessonDelete : function(){
        var lesson_id = $('#lessonId').val();
        var lecture_id = $('#lectureId').val();

        var redirectUrl = '/showLecture/register/take_course/'+lecture_id+'/lesson'

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/lecture/'+lecture_id+'/lesson/delete/'+lesson_id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('수업이 삭제되었습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    },
    noticeDelete : function(){
        var id = $('#noticeId').val();

        $.ajax({
                    type: 'DELETE',
                    url: '/api/v1/notice-delete/'+id,
                    dataType: 'json',
                    contentType:'application/json; charset=utf-8'
                }).done(function() {
                    alert('공지사항이 삭제되었습니다.');
                    window.location.href = '/notice';
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
    },
    lectureLeave : function(){


        var url = '/api/v1/lecture-leave/'+$('#lectureId').val()


        $.ajax({
             type: 'POST',
             url: url,
             contentType:'application/json; charset=utf-8'
         }).done(function() {
             alert('수업에서 탈퇴하셨습니다.');
             window.location.href = '/';
         }).fail(function (error) {
             alert(JSON.stringify(error));
         });
    },
    lectureLessonUpdate : function (){
        var data = {
                        title: $('#title').val(),
                        link: $('#videoLink').val()
                    };


        var isBlank = false;
        var isLink = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.link.trim() == ""){
            isBlank = true;
            isLink = true;
            errorMessage = errorMessage + " [동영상 링크]"
        }

       if (isBlank == true){
            if (isLink == false)
                errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            else
                errorMessage = errorMessage +"가 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }

        var url = '/api/v1/lecture-lesson-update/'+$('#lessonId').val()

         $.ajax({
             type: 'POST',
             url: url,
             dataType: 'json',
             contentType:'application/json; charset=utf-8',
             data: JSON.stringify(data)
         }).done(function() {
             alert('과제 정보가 변경되었습니다.');
             window.location.href = '/showLecture/register/take_course/'+$('#lectureId').val()+"/lesson";
         }).fail(function (error) {
             alert(JSON.stringify(error));
         });

    },
    lectureAssignmentUpdate : function (){

        var dateTime = $('#datePicker').val() +" "+$('#timePicker').val();

        var data = {
                        title: $('#title').val(),
                        content: $('#content').val(),
                        deadline: dateTime
                    };

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.content.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [내용]"
        }
        if (data.deadline.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [데드라인]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }

        var url = '/api/v1/lecture-assignment-update/'+$('#assignmentId').val()

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('과제 정보가 변경되었습니다.');
            window.location.href = '/showLecture/register/take_course/'+$('#lectureId').val()+"/assignment/"+$('#assignmentId').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    lectureUpdate : function (){
            var data = {
                    title: $('#title').val(),
                    outline: $('#outline').val()
                };


            var isBlank = false;
            var errorMessage = "필수 항목";
            if (data.title.trim() == ""){
                isBlank = true;
                errorMessage = errorMessage + " [제목]"
            }
            if (data.outline.trim() == ""){
                isBlank = true;
                errorMessage = errorMessage + " [소개글]"
            }

           if (isBlank == true){
                errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
                alert(errorMessage);
                return;
           }


            var url = '/api/v1/lecture-update/'+$('#lectureId').val()
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('강의 정보가 변경되었습니다.');
                window.location.href = '/showLecture/register/take_course/'+$('#lectureId').val();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });

    },
    noticeUpdate : function (){

            var data = {
                title: $('#title').val(),
                content: $('#content').val()
            };

            var isBlank = false;
            var errorMessage = "필수 항목";
            if (data.title.trim() == ""){
                isBlank = true;
                errorMessage = errorMessage + " [제목]"
            }
            if (data.content.trim() == ""){
                isBlank = true;
                errorMessage = errorMessage + " [내용]"
            }

           if (isBlank == true){
                errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
                alert(errorMessage);
                return;
           }

            var url = '/api/v1/notice-update/'+$('#noticeId').val()

            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType:'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function() {
                alert('공지사항이 등록되었습니다.');
                window.location.href = '/notice';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });

    },
    lectureAssignmentSubmittedFileDownload : function(file_id){
        var url = '/api/v1/file-download/' + file_id;


        $.ajax({
           type: 'POST',
           url: url,
           dataType: 'text',
           contentType:'application/json; charset=utf-8',
       }).done(function() {
           console.log("파일을 다운로드 했습니다.")
       }).fail(function (error) {
           alert(JSON.stringify(error));
       });

    },
    lectureAssignmentSubmittedFileDelete : function(file_id){
        var url = '/api/v1/file-delete/' + file_id;


        $.ajax({
           type: 'DELETE',
           url: url,
           dataType: 'text',
           contentType:'application/json; charset=utf-8',
       }).done(function() {
           window.location.reload();
       }).fail(function (error) {
           alert(JSON.stringify(error));
       });
    },
    lectureAssignmentSubmit : function (){

        var formData = new FormData();
        var inputFile = $('#assignFile')
        var files = inputFile[0].files;

        console.log(files);

        if (files.length == 0 ){
            alert('파일을 선택해주세요.');
            return;
        }

        for (var i = 0 ; i < files.length; i++){
            formData.append("uploadFile",files[i]);
        }

        var url = '/api/v1/file-save/' + $('#assignmentId').val();

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType:  false,
            data: formData
        }).done(function(result) {
            console.log(result)
            if (result == 1){
                alert('과제 제출이 완료되었습니다.');
            }
            else{
                alert('최대 제출 가능 과제 용량은 20MB입니다.');
            }
            window.location.reload();

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    noticeSave : function (){



        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.content.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [내용]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }


        $.ajax({
            type: 'POST',
            url: '/api/v1/notice-save',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(data) {

            alert('공지사항이 등록되었습니다.');
            window.location.href = '/notice';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    lectureAssignmentSave : function (){

        var dateTime = $('#datePicker').val() +" "+$('#timePicker').val();
        console.log(dateTime)

        var data = {
            title : $('#title').val(),
            content : $('#content').val(),
            deadline : dateTime
        };

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.content.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [내용]"
        }
        if (data.deadline.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [데드라인]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }


        var url = "/api/v1/lecture/"+ $('#lectureId').val()+"/assignment/save";
        var redirectUrl = "/showLecture/register/take_course/"+$('#lectureId').val()+"/assignment/"

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('수업 등록을 완료했습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    lectureLessonSave : function (){

        var data = {
            title: $('#title').val(),
            link: $('#videoLink').val()
        };

        var isBlank = false;
        var isLink = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.link.trim() == ""){
            isBlank = true;
            isLink = true;
            errorMessage = errorMessage + " [동영상 링크]"
        }

       if (isBlank == true){
            if (isLink == false)
                errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            else
                errorMessage = errorMessage +"가 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }

        var url = "/api/v1/lecture/"+ $('#lectureId').val()+"/lesson/save";
        var redirectUrl = "/showLecture/register/take_course/"+$('#lectureId').val()+"/lesson/"

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('수업 등록을 완료했습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    },
    lectureNoticeRegistration : function() {


        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        }

        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        }

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.content.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [내용]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }


        var url = "/api/v1/lecture/"+ $('#lectureId').val()+"/notice/save";
        var redirectUrl = "/showLecture/register/take_course/"+$('#lectureId').val()+"/notice/"

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('강좌 공지사항 등록을 완료했습니다.');
            window.location.href = redirectUrl;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    lectureRegistration : function() {


        var data = {
            lecture_id: $('#lectureId').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/lecture_register',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('강좌 수강 신청 완료 했습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    }

    ,lectureSave : function  (){


        var data = {
            title: $('#title').val(),
            outline: $('#outline').val()
        };

        var isBlank = false;
        var errorMessage = "필수 항목";
        if (data.title.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [제목]"
        }
        if (data.outline.trim() == ""){
            isBlank = true;
            errorMessage = errorMessage + " [소개글]"
        }

       if (isBlank == true){
            errorMessage = errorMessage +"이 비어있습니다. 필수 항목은 채워주시길 바랍니다.";
            alert(errorMessage);
            return;
       }



        $.ajax({
            type: 'POST',
            url: '/api/v1/lecture-save',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('강좌가 등록되었습니다.');
            window.location.href = '/showLecture';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var isUserPost = $('#isUserPost').val();
        var isSearchPost = $('#isSearchPost').val();
        var searchContent = $('#searchContent').val();
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            if (typeof isUserPost != "undefined"){
                window.location.href = '/user/posts/inquiry/'+id;
            }
            else if (typeof isSearchPost != "undefined")
                window.location.href = '/search/posts/inquiry/'+id+'?searchContent='+searchContent;
            else
                window.location.href = '/posts/inquiry/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();
        var isUserPost = $('#isUserPost').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            if (isUserPost == true)
                window.location.href = '/user';
            else
                window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    searchPost : function () {

        var searchContent = $('#search-box').val();

        if (searchContent != "") {
            location.href = "/search?searchContent="+searchContent;
        }
        else{
            alert("최소 1글자 이상을 입력해주십시오.")
        }
    },

    commentSave : function (){

        var id = $('#id').val();

        if ($('#comment').val() == ""){
            return false;
        }

        var data = {
            author: $('#commentAuthor').val(),
            content: $('#comment').val()
        };


        $.ajax({
            type: 'POST',
            url: '/api/v1/posts/'+ id +'/comment/',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('댓글이 등록되었습니다.');
            window.location.href = '/posts/inquiry/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    },
    commentDelete : function(post_id, comment_id) {


        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+post_id+'/commentsDelete/'+comment_id,
            dataType: 'Text',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('댓글이 삭제되었습니다.');
            window.location.href = '/posts/inquiry/'+post_id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
     commentUpdate : function (form) {
        var postId = form.querySelector('#postId').value

        const data = {
            id: form.querySelector('#commentId').value,
            content: form.querySelector('#comment-content').value,
            author : form.querySelector('#commentAuthor').value
        }

        if (!data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            $.ajax({
                type: 'PUT',
                url: '/api/v1/posts/' + postId + '/commentsUpdate/' + data.id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }

};


main.init();