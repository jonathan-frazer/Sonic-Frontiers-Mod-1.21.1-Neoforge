package net.sonicrushxii.beyondthehorizon.sonic.baseform.data.enums;

//Stores State, Ensure Default State is False
//For example: When you enter a game, you Have a double jump. So making a HasDoubleJump=True. Wouldn't work
//Instead you must use ConsumedDoubleJump=False.

public enum BaseformState {
    HAS_DOUBLE_JUMP, DANGER_SENSE_ACTIVE,
    GROUND_TRACTION,
    LIGHT_SPEED_STATE, POWER_BOOST_STATE,
}
