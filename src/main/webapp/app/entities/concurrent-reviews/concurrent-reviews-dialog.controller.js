(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConcurrentReviewsDialogController', ConcurrentReviewsDialogController);

    ConcurrentReviewsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ConcurrentReviews', 'TypeLevelCare',
        '$sessionStorage', 'TAB', 'FREQUENCIES'];

    function ConcurrentReviewsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ConcurrentReviews,
                                                TypeLevelCare, $sessionStorage, TAB, FREQUENCIES) {//
        var vm = this;

        vm.Frequencies = extractFrequenciesArray(FREQUENCIES);
        vm.concurrentReviews = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.chart = chart;


        $timeout(function(){
            vm.concurrentReviews.frequency = {id:vm.concurrentReviews.frequency, name:vm.concurrentReviews.frequency};
        }, 250);


        vm.typeLevelCares = TypeLevelCare.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function extractFrequenciesArray(obj) {
            var result = [];
            for (var p in obj) {
                if( obj.hasOwnProperty(p) ) {
                    result.push({id:obj[p], name:obj[p]});
                }
            }
            return result;
        }

        function save () {
            vm.isSaving = true;
            // $sessionStorage.activePatientTab = {activeTab: TAB.CONCURRENT_REVIEW};
            $sessionStorage.activePatientTab = {activeTab: 0};

            if (vm.concurrentReviews.id !== null) {
                // vm.concurrentReviews.chartConcurrentReviews = vm.chart;
                vm.concurrentReviews.status ='ACT';

                vm.concurrentReviews.frequency = vm.concurrentReviews.frequency.id;
                ConcurrentReviews.update(vm.concurrentReviews, onSaveSuccess, onSaveError);
            } else {
                // vm.concurrentReviews.chartConcurrentReviews = vm.chart;
                vm.concurrentReviews.status ='ACT';

                vm.concurrentReviews.frequency = vm.concurrentReviews.frequency.id;
                ConcurrentReviews.save(vm.concurrentReviews, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:concurrentReviewsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.authorizationDate = false;
        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;
        vm.datePickerOpenStatus.lastCoverageDate = false;
        vm.datePickerOpenStatus.nextReviewDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
