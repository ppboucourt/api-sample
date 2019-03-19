(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('XMLConfigDialogController', XMLConfigDialogController);

    XMLConfigDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'XMLConfig', 'Corporation'];

    function XMLConfigDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, XMLConfig, Corporation) {
        var vm = this;

        vm.xMLConfig = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.corporations = Corporation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.xMLConfig.id !== null) {
                XMLConfig.update(vm.xMLConfig, onSaveSuccess, onSaveError);
            } else {
                XMLConfig.save(vm.xMLConfig, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:xMLConfigUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFile = function ($file, xMLConfig) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        xMLConfig.file = base64Data;
                        xMLConfig.fileContentType = $file.type;
                    });
                });
            }
        };

    }
})();
