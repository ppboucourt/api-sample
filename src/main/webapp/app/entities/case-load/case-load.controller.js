(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CaseLoadController', CaseLoadController);

    CaseLoadController.$inject = ['$scope', '$state', 'DataUtils', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService', 'Chart', 'Employee', 'DateUtils'];

    function CaseLoadController ($scope, $state, DataUtils, $q, DTColumnBuilder,
                                 DTOptionsBuilder, GUIUtils, $filter, CoreService, Chart, Employee, DateUtils ) {
        var vm = this;

        //Functions
        vm.search = search;
        vm.loadAll = loadAll;

        //Variable
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.dtInstance = {};
        vm.facility = CoreService.getCurrentFacility();
        vm.therapists = [];
        vm.searchFilter = [];

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Chart.byFacility({id : vm.facility.id}, function(result) {
                    vm.chartsCaseLoad = result;
                    defer.resolve(result);
                    if(result.length){
                        CoreService.capitalizePatientName(result);
                        GUIUtils.icd10CodeToListNames(result[0].icd10S);
                    }
                });
            }else {
                var employees = {employees:{firstName:vm.searchQuery.firstName, lastName: vm.searchQuery.lastName}};
                defer.resolve($filter('filter')(vm.chartsCaseLoad, employees, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Therapist').renderWith(function (data, type, full) {
                return data.employees ? (data.employees.firstName + ' ' + data.employees.lastName) : "Empty";
            }),
            DTColumnBuilder.newColumn('mrNo').withTitle('Mr No.').renderWith(function (data, type, full) {
                return data;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.patient ? data.patient.fullNameCapital : "Empty";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Bed').renderWith(function (data, type, full) {
                return data.bed ? data.bed.name : "Empty";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Substance').renderWith(function (data, type, full) {
                return data.typePatientPrograms ? data.typePatientPrograms.name : "Empty";
            }),
            DTColumnBuilder.newColumn('admissionDate').withTitle('Admission Date').renderWith(function (data, type, full) {
                return CoreService.parseToDate(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Diagnosis Codes').renderWith(function (data, type, full) {
                return data.icd10S ? GUIUtils.icd10CodeToListNames(data.icd10S) : 'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            Employee.therapists({teid: 2}, onSuccessEmployee, onErrorEmployee);
        }

        function onSuccessEmployee(result) {
            vm.therapists = result;
            for(var i = 0; i < result.length; i++){
                result[i].valueToShow = result[i].firstName + ' ' + result[i].lastName;
            }
            vm.searchQuery = null;
        }

        function onErrorEmployee(error) {
            console.log('Failed getting patients: ' + error);
        }

        function actionsHtml(data){
            return '<a class="btn-sm btn-primary" ' +
            // 'ui-sref="contacts-facility.edit({id:' + data.id + '})" href="#/contacts-facility/' + data.id + '/edit"'+
                '>' +
                '<i class="fa fa-eye"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
