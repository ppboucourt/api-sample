(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedPassController', MedPassController);

    MedPassController.$inject = ['$scope', '$state', 'DataUtils', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService', 'Chart', 'Employee', 'DateUtils', 'ChartToMedications', '$compile'];

    function MedPassController ($scope, $state, DataUtils, $q, DTColumnBuilder,
                                 DTOptionsBuilder, GUIUtils, $filter, CoreService, Chart, Employee, DateUtils, ChartToMedications, $compile ) {
        var vm = this;

        //Functions
        vm.search = search;
        vm.loadAll = loadAll;
        vm.changeTakenNo = changeTakenNo;
        vm.changeTakenYes = changeTakenYes;

        //Variable
        vm.dtInstance = {};
        vm.facility = CoreService.getCurrentFacility();
        vm.prescriptionTimes = [];
        vm.searchFilter = [];
        vm.chartMedDistinct = [];
        vm.chartMedicationsToSave = {};

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                ChartToMedications.medicationsChartByFacility({id : vm.facility.id}, function(result) {
                    vm.chartMedications = result;
                    defer.resolve(result);
                    prepareData(result);
                });
            }else {
                var searchCriteria = {prescriptionTime: vm.searchQuery.prescriptionTime};
                defer.resolve($filter('filter')(vm.chartMedications, searchCriteria, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
                function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                    $compile(nRow)($scope);
                });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Times').renderWith(function (data, type, full) {
                return data.prescriptionTime;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Mr No.').renderWith(function (data, type, full) {
                return data.chart.mr_no;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.chart.patient ? data.chart.patient.fullNameCapital : "Empty";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Bed').renderWith(function (data, type, full) {
                return data.chart.bed ? data.chart.bed.name : "Empty";
            }),
            DTColumnBuilder.newColumn('medication').withTitle('Medications').renderWith(function (data, type, full) {
                return data.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Taken').withOption('with', '45px').notSortable()
                .renderWith(takenHtml)
            ,
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToMedications.prescriptionDistinct({id : vm.facility.id}, function(result) {
                var time;
                for(var i=0; i < result.length; i++){
                    time = {};
                    time.id = i;
                    time.prescriptionTime = moment(result[i]).format('hh:mm a');
                    time.prescriptionDate = moment(result[i]).format('MM/dd/YYYY');
                    vm.chartMedDistinct.push(time);
                }
            });
        }

        function actionsHtml(data){
            return '<a class="btn-sm btn-primary" ' +
            // 'ui-sref="contacts-facility.edit({id:' + data.id + '})" href="#/contacts-facility/' + data.id + '/edit"'+
                '>' +
                '<i class="fa fa-eye"></i></a>';
        }

        function takenHtml(data) {
                return '&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-success" ng-click="vm.changeTakenYes(' + data.id + ')" ' +
                    'style="cursor: pointer;">Yes</span></td>&nbsp;&nbsp;&nbsp;&nbsp;' +
                    '<td><span class="label label-danger" ng-click="vm.changeTakenNo('+ data.id +')" ' +
                    'style="cursor: pointer;" role="button" tabindex="0">No</span></td>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function prepareData(data) {
            for(var i=0; i < data.length; i++){
                data[i].prescriptionTime = moment(data[i].datePrescription).format('hh:mm a');
                data[i].prescriptionDate = moment(data[i].datePrescription).format('MM/dd/yyyy');
                data[i].chart.patient.fullNameCapital = CoreService.fullNameCapitalized(data[i].chart.patient);
            }
        }

        function changeTakenNo(id) {
            var i = 0;
            for (i = 0; vm.chartMedications[i].id != id; i++);

            vm.chartMedications[i].taken = false;
            angular.copy(vm.chartMedications[i], vm.chartMedicationsToSave);
            saveChartToMedication();
        }

        function changeTakenYes(id) {
            var i = 0;
            for (i = 0; vm.chartMedications[i].id != id; i++);

            vm.chartMedications[i].taken = true;
            angular.copy(vm.chartMedications[i], vm.chartMedicationsToSave);
            saveChartToMedication();
        }

        function saveChartToMedication() {
            ChartToMedications.update(vm.chartMedicationsToSave, onSuccessChartMedication, onErrorChartMedication);
        }

        function onSuccessChartMedication(result) {
            vm.searchQuery = null;
            vm.dtInstance.reloadData();
        }

        function onErrorChartMedication(error) {
            console.log('Failed getting contacts facility: ' + error);
        }

    }
})();
