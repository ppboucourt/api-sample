(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ShiftsEditController', ShiftsDetailController);

    ShiftsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Shifts',
        'SystemService', '$state', 'CoreService', '$q'];

    function ShiftsDetailController($scope, $rootScope, $stateParams, previousState, entity, Shifts, SystemService,
                                    $state, CoreService, $q) {
        var vm = this;
        vm.changeEditingState = changeEditingState;
        vm.save = save;
        vm.form = {};
        vm.shifts = entity;
        vm.previousState = previousState.name;

        Shifts.currentPatients({id: CoreService.getCurrentFacility().id}).$promise.then(function (data) {
                vm.currentPatients = data;
            }, function (reason) {
                console.log('Failed getting employee: ' + reason);
            }
        );

        vm.attachFile = function (result, successResult, errorResult) {
            SystemService.attachShiftFile(result, successResult, errorResult);
        }

        vm.deleteFile = function (fileId) {
            var defer = $q.defer();
            SystemService.deleteShiftFile({id: fileId},
                function success(data) {
                    defer.resolve(data);
                }, function error(error) {
                    defer.reject(error);
                });
            return defer.promise;
        }

        var unsubscribe = $rootScope.$on('bluebookApp:shiftsUpdate', function (event, result) {
            vm.shifts = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        function save() {
            vm.isSaving = true;
            Shifts.update(vm.shifts, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $scope.$emit('bluebookApp:labProfileUpdate', result);
            vm.isSaving = false;
            $state.go('shifts', null, {reload: 'shifts'});
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
