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

function goTo(url)
{
   window.location.href = url;
}