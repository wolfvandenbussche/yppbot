package ui.controllers;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.logging.LogManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import utility.PuzzlePirates;

public abstract class AbstractGameController extends AbstractController {

	Point stageTopLeft;
	public void initializeGame() {
		Rectangle puzzlePiratesBounds = PuzzlePirates.getPuzzlePiratesBounds();
		Point upperLeftPuzzle = this.getUpperLeft();

		KeyCombination kc = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
		Runnable rn = () -> System.out.println("Accelerator key worked");
		this.getScene().getAccelerators().put(kc, rn);

		stageTopLeft = new Point(puzzlePiratesBounds.x + upperLeftPuzzle.x,puzzlePiratesBounds.y + upperLeftPuzzle.y);
		this.getSecondaryStage().setX(stageTopLeft.x);
		this.getSecondaryStage().setY(stageTopLeft.y);

		enableKeyBinds();
	}

	public Point getPuzzleTopLeft() {
		return stageTopLeft;
	}
	
	public void close() {
		this.getPrimaryStage().close();
	}

	private void enableKeyBinds() {
		try {
			LogManager.getLogManager().reset();
			GlobalScreen.setEventDispatcher(new SwingDispatchService());
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		
		
		GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
			public void nativeKeyPressed(NativeKeyEvent e) {
			}

			public void nativeKeyReleased(NativeKeyEvent e) {
				if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
					System.out.println("Exit");
					// Moet in een FX thread zijn
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							AbstractGameController.this.getPrimaryStage().close();
							System.exit(0);
						}
					});
				}

				if (e.getKeyCode() == NativeKeyEvent.VC_R) {
					// Moet in een FX thread zijn
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							AbstractGameController.this.pressKey();
						}
					});
				}
			}

			public void nativeKeyTyped(NativeKeyEvent e) {
			}
		});
		
		GlobalScreen.addNativeMouseListener(new NativeMouseListener () {

			@Override
			public void nativeMouseClicked(NativeMouseEvent e) {
			}

			@Override
			public void nativeMousePressed(NativeMouseEvent e) {
			}

			@Override
			public void nativeMouseReleased(NativeMouseEvent e) {
				// Moet in een FX thread zijn
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						AbstractGameController.this.clickMouse();
					}
				});
				
			}
			
		});
	}

	public abstract void pressKey();
	
	public abstract void clickMouse();

	public abstract Point getUpperLeft();
}
