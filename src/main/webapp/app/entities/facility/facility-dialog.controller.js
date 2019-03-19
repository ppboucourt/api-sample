(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FacilityDialogController', FacilityDialogController);

    FacilityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Facility',
        'Building', 'ContactsFacility', 'Corporation', 'CoreService', '$rootScope', 'CountryState'];

    function FacilityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Facility,
                                       Building, ContactsFacility, Corporation, CoreService, $rootScope, CountryState ) {
        var vm = this;

        vm.form = {};
        vm.facility = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.buildings = Building.query();
        vm.states = CountryState.query();
        vm.contactsfacilities = ContactsFacility.query();
        vm.corporations = Corporation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facility.id !== null) {
                vm.facility.status = 'ACT';
                vm.facility.corporation = CoreService.getCorporation();
                Facility.update(vm.facility, onSaveSuccess, onSaveError);
            } else {
                vm.facility.status = 'ACT';
                vm.facility.corporation = CoreService.getCorporation();
                Facility.save(vm.facility, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:facilityUpdate', result);
            $rootScope.$broadcast('bluebookApp:updateFacilities', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setLogo = function ($file, facility) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        facility.logo = base64Data;
                        facility.logoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
