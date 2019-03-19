(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FieldsDetailController', FieldsDetailController);

    FieldsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Fields', 'Tables', 'ReportDetails'];

    function FieldsDetailController($scope, $rootScope, $stateParams, previousState, entity, Fields, Tables, ReportDetails) {
        var vm = this;

        vm.fields = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:fieldsUpdate', function(event, result) {
            vm.fields = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
