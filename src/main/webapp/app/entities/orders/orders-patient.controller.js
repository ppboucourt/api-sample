(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrdersPatientController', OrdersPatientController);

    OrdersPatientController.$inject = ['CoreService', 'chart', '$state'];

    function OrdersPatientController (CoreService, chart, $state ) {
        var vm = this;

        //Functions
        vm.backDetailsBasic = backDetailsBasic;

        //Variable
        vm.patientProcessForms = [];

        vm.activeTab = 1;

        loadAll();
        function loadAll() {
        }

        function backDetailsBasic() {
            $state.go('patient', {}, {reload: true});
        }
    }
})();
