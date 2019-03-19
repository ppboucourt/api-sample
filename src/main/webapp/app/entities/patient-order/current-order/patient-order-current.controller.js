(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderCurrentController', PatientOrderCurrentController);

    PatientOrderCurrentController.$inject = ['$state', '$scope'];

    function PatientOrderCurrentController($state, $scope) {
        var vm = this;
        vm.backDetailsBasic = backDetailsBasic;
        vm.keepActiveTab = keepActiveTab;

        // vm.activeTab = $state.params.orderType == 'ONE_TIME' ? 0 : $state.params.orderType == 'STANDING' ? 2 : 1;
        vm.activeTab = 1;
        function backDetailsBasic() {
            $state.go('patient', {}, {reload: true});
        }

        function keepActiveTab(tab) {
            //Lab orders
            if (tab == 0) {
                $scope.$emit('bluebookApp:clickTabLabOrders');
            }
        }
    }
})();
