package mms.helper.ui.fm7;

import mms.ui.UIScreen;

public class HelperFM7 extends mms.helper.ui.HelperUI
{
  public HelperFM7() {}
  
  public static String appDialogPreloaderOpen(UIScreen s, String title) {
    String ret = String.valueOf(mms.helper.ui.UniqueID.get());
    String t = jsSingleQuote(title);
    s.executeJSView("var d_" + ret + "= app.dialog.preloader('" + t + "');");
    
    return ret;
  }
  
  public static void appDialogPreloaderClose(UIScreen s, String idDialog) {
    s.executeJSView("d_" + idDialog + ".close();");
  }
  
  public static void viewRouterNavigate(UIScreen s, String viewVar, String url, String jsobject_options) {
    if (jsobject_options != null) {
      s.executeJSView(viewVar + ".router.navigate('" + url + "'," + jsobject_options + ")");
    } else
      s.executeJSView(viewVar + ".router.navigate('" + url + "')");
  }
  
  public static void routerNavigate(UIScreen s, String viewVar, String url) {
    viewRouterNavigate(s, viewVar, url, null);
  }
}
