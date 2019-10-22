package utility;

import java.awt.Rectangle;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public class ExternalWindowManager {

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

		HWND FindWindow(String lpClassName, String lpWindowName);

		int GetWindowRect(HWND handle, int[] rect);

		boolean SetForegroundWindow(HWND hWnd);
	}

	private static HWND hwnd;

	public static boolean isWindowAvailable(String windowName) {
		ExternalWindowManager.hwnd = User32.INSTANCE.FindWindow(null, windowName);
		if (hwnd == null) {
			return false;
		}
		return true;
	}

	public static Rectangle getWindowBounds() {
		int[] rect = { 0, 0, 0, 0 };
		int result = User32.INSTANCE.GetWindowRect(hwnd, rect);
		if (result == 0) {
			return null;
		}
		return new Rectangle(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
	}

	public static void restoreWindow() {
		User32.INSTANCE.SetForegroundWindow(hwnd);
	}

}
