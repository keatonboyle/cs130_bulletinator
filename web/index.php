<!DOCTYPE html>
<html>
   <head>
      <title>The VBB</title>
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
            <div id="login-form">
               <form id="login" action="verify.php" method="post">
                  <div class="label required" for="username">Username</div>
                  <input type="text" name="username">
                  <div class="label required" for="password">Password</div>
                  <input type="password" name="password" onkeypress="hitEnter(event)">
               </form>
               <div id="button-spacing">
                  <span class="button large blue mr10" onclick="submitForm()">Log In</span>
                  <span class="button large green" onclick="goTo('newAccount.php')">Sign Up</span>
               </div>
            </div>
            <img id="phone-demo" src="resources/images/n4-shell.png">
         </div>
      </main>
      <footer>
         <div class="wrap">
         </div>
      </footer>
   </body>
   <script src="/~cs130s/jsFunctions.js"></script>
</html>