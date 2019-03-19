(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ArchivePatientController', ArchivePatientController)
        .filter('startFrom', function() {
            return function(input, start) {
                start = +start; //parse to int
                return input.slice(start);
            }
        });

    ArchivePatientController.$inject = ['$scope', '$state', 'DataUtils', 'Chart', 'ChartSearch', 'DTColumnBuilder',
        'CoreService', 'TypePatientPrograms', '$filter', 'chart', 'DateUtils'];

    function ArchivePatientController ($scope, $state, DataUtils, Chart, ChartSearch, GUIUtils,
                                       CoreService, TypePatientPrograms, $filter, chart, DateUtils ) {
        var vm = this;

        //Variables
        var facility = CoreService.getCurrentFacility();
        vm.chart = chart;
        vm.currentCharts = [];
        vm.currentChartsCopy = [];
        $scope.currentPage = 0;
        $scope.pageSize = 18;
        $scope.today = new Date();
        $scope.dateformat = "MM/dd/yyyy";
        vm.typePatientProgram = TypePatientPrograms.query();
        vm.showFilter = false;
        vm.facility = CoreService.getCurrentFacility();
        vm.emptyMssg = false;

        //Functions
        vm.search = search;
        vm.loadAll = loadAll;
        vm.patientDetails = patientDetails;
        vm.popoverFilterEnable = popoverFilterEnable;
        vm.openCalendar = openCalendar;
        vm.filter = filter;
        vm.clear = clear;

        loadAll();

        function loadAll() {
            if(!vm.searchQuery || vm.searchQuery == ''){
                Chart.archive({id: facility.id}, function(result) {
                    vm.currentCharts = result;
                    CoreService.fullNameCapitalizedList(result);
                  //  CoreService.fullNameCapitalizedList(vm.currentCharts);

                    console.log(vm.currentCharts );

                    angular.copy(vm.currentCharts, vm.currentChartsCopy);
                    //For ng-repeat's pagination
                    $scope.numberOfPages=function(){
                        return Math.ceil(vm.currentCharts.length/$scope.pageSize);
                    }
                    vm.emptyMssg = result.length > 0 ? false : true;
                });
            }else {
                //For ng-repeat's pagination
                vm.currentCharts = $filter('filter')(vm.currentChartsCopy, vm.searchQuery, undefined);
                $scope.numberOfPages = function () {
                    return Math.ceil(vm.currentCharts.length / $scope.pageSize);
                }
            }
        }

        function search() {
            loadAll();
        }

        function patientDetails(id) {
            $state.go('patient', {id: id});
        }

        function popoverFilterEnable(){
            vm.showFilter = !vm.showFilter;
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.admissionDateFrom = false;
        vm.datePickerOpenStatus.admissionDateTo = false
        vm.datePickerOpenStatus.dischargeDateFrom = false;
        vm.datePickerOpenStatus.dischargeDateTo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function filter() {
            vm.isFilter = true;

            var must = [];
            must.push({term: {"facility.id": vm.facility.id}});
            must.push({term: {"delStatus": false}});

            if (vm.chart.firstName != null && vm.chart.firstName.length > 0){
                must.push({match: {"patient.firstName": {query : vm.chart.firstName}}});
            }

            if (vm.chart.lastName != null && vm.chart.lastName.length > 0){
                must.push({match: {"patient.lastName": {query :vm.chart.lastName}}});
            }

            if (vm.chart.admissionDate != null && vm.chart.admissionDate.length > 0){
                must.push({match: {admissionDate: {query :vm.chart.admissionDate}}});
            }

            if (vm.chart.mrNo != null && vm.chart.mrNo.length > 0){
                must.push({term: {mrNo: vm.chart.mrNo}});
            }

            if (vm.chart.typePatientProgram != null && vm.chart.typePatientProgram.length > 0){
                must.push({term: {"typePatientProgram.id" :vm.chart.typePatientProgram.id}});
            }

            if (vm.chart.dischargeDateFrom) {
                must.push({range: {dischargeDate: {gte :DateUtils.convertLocalDateToServer(vm.chart.dischargeDateFrom)}}});
            }

            if (vm.chart.dischargeDateTo) {
                must.push({range: {dischargeDate: {lte :DateUtils.convertLocalDateToServer(vm.chart.dischargeDateTo)}}});
            }

            if (vm.chart.admissionDateFrom) {
                must.push({range: {admissionDate: {gte :DateUtils.convertLocalDateToServer(vm.chart.admissionDateFrom)}}});
            }

            if (vm.chart.admissionDateTo) {
                must.push({range: {admissionDate: {lte :DateUtils.convertLocalDateToServer(vm.chart.admissionDateTo)}}});
            }
            if(must.length > 2){
                ChartSearch.query(
                    {
                        query: {
                            bool: {
                                must: must
                            }
                        }
                    }, function(result) {
                        vm.currentCharts = result;
                        CoreService.fullNameCapitalizedList(result);
                        vm.emptyMssg = result.length > 0 ? false : true;
                        // vm.dtInstance.reloadData();
                    });
            }else{
                alert('Ther is not data for filtering');
            }
            popoverFilterEnable();
        }

        function clear() {
            vm.chart = {
                firstName: null,
                lastName: null,
                mrNo: null,
                dischargeDateFrom: null,
                dischargeDateTo: null,
                admissionDateFrom: null,
                admissionDateTo: null,
                typePatientProgram: {}
            }
            if(vm.currentChartsCopy.length > 0){
                vm.currentCharts = vm.currentChartsCopy;
            }else{
                loadAll();
            }
        }

    }

})();
