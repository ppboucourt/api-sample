(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('SignController', SignController);

    SignController.$inject = ['JSignature', 'CoreService', '$state', '$rootScope', 'Employee'];//, 'previousState'

    function SignController(JSignature, CoreService, $state, $rootScope, Employee) {
        var vm = this;

        $rootScope.background = '';

        vm.result = {
            agree: false,
            signed: true
        }

        vm.save = save;

        vm.getData = getData;
        vm.enable = enable;
        vm.disable = disable;

        //We check if the employee has signed we redirect to the previous state
        vm.employee = CoreService.getCurrentEmployee();


        function save() {

            if (!JSignature.getData(JSignature.exportTypes.NATIVE).length) {
                alert("Please sign and then press finish!!!");
            } else {
                var sign = JSignature.getData(JSignature.exportTypes.IMAGE_PNG_BASE64);

                vm.employee.signature = sign;
                CoreService.setCurrentEmployee(vm.employee);
                Employee.update(vm.employee, onSaveSuccess, onSaveError);
            }

        }

        function onSaveSuccess(result) {
            $state.go("home");
        }

        function onSaveError() {
            vm.isError = false;
        }

        function getData() {
            var data = JSignature.getData(JSignature.exportTypes.IMAGE_SVG_XML_BASE64);
            var i = new Image();
            i.src = 'data:' + data[0] + ',' + data[1];
            vm.hash = 'data:' + data[0] + ',' + data[1];
        }

        function enable() {
            JSignature.enable();
        }

        function disable() {
            JSignature.disable();
        }

    }
})
();
