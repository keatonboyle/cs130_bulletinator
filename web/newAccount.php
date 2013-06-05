<!DOCTYPE html>
<html>
   <head>
      <title>VBB: Signup</title>
      <link rel="stylesheet" type="text/css" href="styles.css">
      <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
   </head>
   <body>
      <header>
         <div class="wrap">
            <center id="logo">Bulletinator</center>
         </div>
      </header>
      <main>
         <div class="wrap">
            <div class="block-center">
               <form action="new_account_success.php" method="post">
                  <div class="label required" for="username">Username</div>
                  <input type="text" name="username">
                  <div class="label required" for="email">E-mail</div>
                  <input type="text" name="email">
                  <div class="label required" for="password">Password</div>
                  <input type="password" name="password">
                  <div class="label required" for="passwordVerify">Retype password</div>
                  <input type="password" name="passwordVerify" onkeypress="hitEnter(event)">
               </form>
               <div id="button-spacing">
                  <span class="button large blue first" onclick="submitForm()">Submit</span>
                  <span class="button large red" onclick="goTo('.')">Cancel</span>
               </div>
            </div>
         </div>
      </main>
   </body>
   <script src="/~cs130s/jsFunctions.js"></script>
</html>