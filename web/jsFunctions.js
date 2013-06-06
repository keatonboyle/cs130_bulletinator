function hitEnter(e)
{
   if(e.keyCode == 13)
   {
      submitForm();
   }
}

function submitForm()
{
   var anyBad = false;
   var required = $(".label.required");
   for(var ii = 0; ii < required.length; ii++)
   {
      var name = $(required[ii]).attr("for");
      var input = $("input[name='" + name + "']");
      if($(input).val() == '')
      {
         $(required[ii]).addClass("red-text");
         anyBad = true;
      }
      else
      {
         $(required[ii]).removeClass("red-text");
      }
   }

   if(!anyBad)
   {
      $("form").submit();
   }
}

function submitFormById(id)
{
   $("form#" + id).submit();
}

function goTo(url)
{
   window.location.href = url;
}

function selectBtn(btn)
{
   $(".btn-tab").removeClass("selected");
   $(btn).addClass("selected");
   $(".bulletin-wrap").fadeOut(200, 
      function () {
         $(this).empty();
         $(this).removeClass("v-align");
         $(this).addClass("rel");
         var bulletin_id = $(btn)
                              .children("input[name='bulletin_id']")
                              .attr("value");
         var username = $(btn)
                           .children("input[name='username']")
                           .attr("value");

         var data = 'bulletin_id=' + bulletin_id;
         showBulletin(data, username);
      });
   
}

function showBulletin(data, username)
{
   $.ajax({
      url: 'viewBulletin.php',
      data: data,
      type: 'POST',
      dataType: 'JSON'
   })
   .done(function (response) {
      if(response.success)
      {
         var bulletin_html = '<span id="delete-button" onclick="submitFormById(\'delete-form\')">Delete</span>' +
                              '<form class="nodisp" action="deleteBulletin.php" method="post" id="delete-form">' +
                                 '<input type="hidden" name="bulletin_id" value="' + response.bulletin_id + '">' +
                                 '<input type="hidden" name="username" value="' + username + '">' +
                              '</form>';
         bulletin_html += '<table border="0">' +
                              '<tr><td>Bulletin ID:</td><td>' + response.bulletin_id + '</td></tr>' +
                              '<tr><td>Title:</td><td>' + response.title + '</td></tr>' +
                              '<tr><td>Description:</td><td>' + response.shortdesc + '</td></tr>' +
                              '<tr><td>Body Text:</td><td>' + response.bodytext + '</td></tr>' +
                              '<tr><td>Contact:</td><td>' + response.contact + '</td></tr>' +
                              '<tr><td>Category:</td><td>' + response.category + '</td></tr>' +
                              '<tr><td>Expiration:</td><td>' + response.expiration + '</td></tr>';
         if(response.file_id != null)
         {
            bulletin_html += '<tr><td>File:</td><td><img style="max-width:300px" src="/~cs130s/resources/bulletins/' + 
                                    response.file_id + '.' + response.file_ext + '" alt="File: ' + response.file_id + '">' + '</td></tr>';
         }
         bulletin_html += '</table>';

         $(".bulletin-wrap")
            .append(bulletin_html)
            .fadeIn(200);
      }
   });
}