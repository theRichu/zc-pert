$(document).ready(function(){
   if($("#sso_username") && $("#ssopassword")) {
      if(localStorage["sso_username"]) {
         $("#sso_username").val(sjcl.decrypt("sso_username", localStorage["sso_username"]));
      }
      if(localStorage["sso_password"]){
         $("#ssopassword").val(sjcl.decrypt("sso_password", localStorage["sso_password"]));
      }
      
      $("a.buttonLinkText").live("click", function(){
         storeSSOCredential();
      });
      
      $("#sso_username").keypress(function(event){
         if(event.keyCode == "13") {
            storeSSOCredential();
         }
         
      });
      $("#ssopassword").keypress(function(event){
         if(event.keyCode == "13") {
            storeSSOCredential();
         }
      });
   }
});

function storeSSOCredential() {
   localStorage["sso_username"] = sjcl.encrypt("sso_username", $("#sso_username").val());
   localStorage["sso_password"] = sjcl.encrypt("sso_password", $("#ssopassword").val());;   
}
