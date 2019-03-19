(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('AdminLTEService', AdminLTEService);

    function AdminLTEService() {

        var service = {
            registerTreeActions : registerTreeActions,
            registerPushMenuActions: registerPushMenuActions,
            registerBoxWidget: registerBoxWidget,
            registerControlSidebar: registerControlSidebar,
            registerICheckWidget: registerICheckWidget,
            layoutFix: layoutFix
        };

        return service;

        function registerTreeActions(menu) {

            $.AdminLTE.tree(menu);
        }

        function registerPushMenuActions(toggleBtn) {

            $.AdminLTE.pushMenu.activate(toggleBtn);
        }

        function registerBoxWidget() {

            $.AdminLTE.boxWidget.activate();
        }

        function registerControlSidebar() {

            $.AdminLTE.controlSidebar.activate();
        }

        function registerICheckWidget() {

            //iCheck for checkbox and radio inputs
            $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                checkboxClass: 'icheckbox_minimal-blue',
                radioClass: 'iradio_minimal-blue'
            });
            //Red color scheme for iCheck
            $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
                checkboxClass: 'icheckbox_minimal-red',
                radioClass: 'iradio_minimal-red'
            });
            //Flat red color scheme for iCheck
            $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
                checkboxClass: 'icheckbox_flat-green',
                radioClass: 'iradio_flat-green'
            });
        }

        function layoutFix() {
            $.AdminLTE.layout.activate();
        }
    }
})();
