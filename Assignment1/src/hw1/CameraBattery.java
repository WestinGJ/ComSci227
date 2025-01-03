package hw1;
/**
 * This class represents a camera battery
 * @author Westin Gjervold
 */
public class CameraBattery {
	/*
	 * Sets the maximum number of external charger settings. 
	 */
	public static final int NUM_CHARGER_SETTINGS = 4;
	/*
	 * Sets the charge rate
	 */
	public static final double CHARGE_RATE = 2.0;
	/*
	 * Sets the default camera power consumption
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;
	/*
	 * Holds the charger setting
	 */
	private int charger_setting;
	/*
	 * Holds the camera charge
	 */
	private double camera_charge;
	/*
	 * Holds the battery charge
	 */
	private double battery_charge;
	/*
	 * Holds the battery capacity
	 */
	private double battery_capacity;
	/*
	 * Holds the camera power consumption 
	 */
	private double camera_power_consumption;
	/*
	 * Holds the total drain amount
	 */
	private double total_drain;
	/*
	 * Connection status of battery and camera
	 */
	private double battery_connected_to_camera;
	/*
	 * Connection status of battery and external charger
	 */
	private double battery_connected_to_external_charger;
	/*
	 * Constructs new camera battery
	 * @param batteryStartingCharge: Initial battery charge
	 * @param batteryCapacity: Sets maximum battery charge
	 */
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		battery_charge = Math.min(batteryStartingCharge, batteryCapacity);
		battery_capacity = batteryCapacity;
		battery_connected_to_camera = 0;
		battery_connected_to_external_charger = 0;
		charger_setting = 0;
		camera_power_consumption = DEFAULT_CAMERA_POWER_CONSUMPTION;
		
	}
	/*
	 * Adds one to the charger setting 
	 */
	public void buttonPress() {
		charger_setting = (charger_setting + 1) % NUM_CHARGER_SETTINGS;
	}
	/*
	 * Charges the battery connected to the battery
	 * @param minutes: Sets number of minutes charged
	 * @return amount battery charged
	 */
	public double cameraCharge(double minutes) {
		double cameraCharge = Math.min((minutes * CHARGE_RATE), (battery_capacity - battery_charge));
		camera_charge += Math.min(battery_connected_to_camera, cameraCharge);
		battery_charge += Math.min(battery_connected_to_camera, cameraCharge);
		camera_charge = Math.min(camera_charge, battery_capacity);
		battery_charge = Math.min(battery_charge, battery_capacity);
		return cameraCharge;
	}
	/*
	 * Drains the battery connected to the battery
	 * @param minutes: Sets number of minutes drained
	 * @return amount battery drained 
	 */
	public double drain(double minutes) {
		double drain = Math.min(battery_charge, (minutes * camera_power_consumption));
		camera_charge -= Math.min(battery_connected_to_camera, drain);
		battery_charge -= Math.min(battery_connected_to_camera, drain);
		total_drain += Math.min(battery_connected_to_camera, drain);
		return drain;
	}
	/*
	 * Charges the battery connected to the external charger
	 * @param minutes: Set number of minutes charged
	 * @return amount battery charged
	 */
	public double externalCharge(double minutes) {
		double batteryCharge = Math.min((charger_setting * minutes * CHARGE_RATE), (battery_capacity - battery_charge));
		battery_charge += Math.min(batteryCharge, battery_connected_to_external_charger);
		battery_charge = Math.min(battery_charge, battery_capacity);
		return batteryCharge;
	}
	/*
	 * Resets total drain to 0
	 */
	public void resetBatteryMonitor() {
		total_drain = 0;
	}
	/*
	 * Gets battery's capacity
	 * @return battery capacity
	 */
	public double getBatteryCapacity() {
		return battery_capacity; 
	}
	/*
	 * Gets battery charge
	 * @return battery charge
	 */
	public double getBatteryCharge() {
		return battery_charge;
	}
	/*
	 * Gets camera charge
	 * @return camera charge
	 */
	public double getCameraCharge() {
		return camera_charge;
	}
	/*
	 * Gets camera power consumption
	 * @return camera power consumption
	 */
	public double getCameraPowerConsumption() {
		return camera_power_consumption;
	}
	/*
	 * Gets charger setting 
	 * @return charger setting
	 */
	public int getChargerSetting() {
		return charger_setting;
	}
	/*
	 * Gets total drain
	 * @return total drain
	 */
	public double getTotalDrain() {
		return total_drain;
	}
	/*
	 * Moves battery to exteral charger
	 */
	public void moveBatteryExternal() {
		battery_connected_to_external_charger = battery_capacity;
	}
	/*
	 * Moves battery to camera
	 */
	public void moveBatteryCamera() {
		camera_charge = battery_charge;
		battery_charge = camera_charge;
		battery_connected_to_camera = battery_capacity;
	}
	/*
	 * Removes battery from external charger and camera
	 */
	public void removeBattery() {
		battery_connected_to_external_charger = 0;
		battery_connected_to_camera = 0;
		camera_charge = 0;
	}
	/*
	 * Sets camera power consumption
	 * @param cameraPowerConsumption: Sets camera power consumption
	 */
	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		camera_power_consumption = cameraPowerConsumption;
	}
}
