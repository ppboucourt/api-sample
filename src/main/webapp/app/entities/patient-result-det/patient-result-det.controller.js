(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultDetController', PatientResultDetController);

    PatientResultDetController.$inject = ['patientResults', '$scope', '$state', 'PatientResultDet', '$compile',
        'PatientResultDetSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', 'PatientResultFile'];

    function PatientResultDetController (patientResults, $scope, $state, PatientResultDet, $compile,
                                         PatientResultDetSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, PatientResultFile) {
        var vm = this;

        vm.patientResultDets = [];
        vm.search = search;
        vm.patientResults = patientResults;
        vm.dtInstance = {};
        vm.pdf = {};

        vm.dtColumns = [
            DTColumnBuilder.newColumn('testCode').withTitle('Test Code'),
            DTColumnBuilder.newColumn('testName').withTitle('Test'),
            DTColumnBuilder.newColumn('result').withTitle('Result'),
            DTColumnBuilder.newColumn('cutOff').withTitle('CutOFF'),
            DTColumnBuilder.newColumn('units').withTitle('Units'),
            DTColumnBuilder.newColumn('').withTitle('Status').renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            if (full.status == 'N') {
                return GUIUtils.getStatusTemplate(full.status, 'success');
            } else {
                return GUIUtils.getStatusTemplate(full.status, 'danger');
            }
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientResultDet.query({id: vm.patientResults.id}, function (result) {
                    vm.patientResultDets = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.patientResultDets, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.getPDF = function () {
            PatientResultFile.getPdf(vm.pdf.id);
        }
    }
})();
