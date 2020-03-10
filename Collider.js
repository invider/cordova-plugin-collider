/**
 * Collider.JAM cordova plugin
 * 
 * Obejct bellow is exported into window.plugins.collider
 */
var self = module.exports = {

    LONG_PRESS: 0,
    VIRTUAL_KEY: 1,
	KEYBOARD_TAP: 3,

	tap: function(type) {
        if (!type) type = self.LONG_PRESS

		cordova.exec(
            function() {},
            function() {}, 
            'Collider', 
            'vibrate', 
            [type]
		)
	},

    bing: function() {
        cordova.exec(
            function() {},
            function() {}, 
            'Collider', 
            'bing', 
            []
        )
    },

    config: function(success) {
        if (typeof success !== 'function') throw 'feedback function is expected'

        cordova.exec(
            success,
            function() {}, 
            'Collider', 
            'config', 
            []
        )
    },
}
