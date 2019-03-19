(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MarsPatientController', MarsPatientController);

    MarsPatientController.$inject = ['TypePatientProcess', 'CoreService', 'chart', '$state', '$scope', 'PatientAction'];

    function MarsPatientController (TypePatientProcess, CoreService, chart, $state, $scope, PatientAction) {
        var vm = this;

        //Functions
        vm.backDetailsBasic = backDetailsBasic;

        //Variable
        vm.patientProcessForms = [];

        vm.activeTab = 1;

        loadAll();
        function loadAll() {
            TypePatientProcess.byTypePage({pagId: 1, facId: CoreService.getCurrentFacility().id}, function (result) {
                vm.patientProcessForms = result;
            });
        }

        function backDetailsBasic() {
            $state.go('patient', {}, {reload: true});
        }

        $scope.$on('bluebookApp:actions', function(event, result) {
            vm.actions = result.count;
        });

        $scope.$on('bluebookApp:medications', function(event, result) {
            vm.medications = result.count;
        });
    }
})();
