(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['$scope', 'CoreService', 'Chart', 'DTColumnBuilder', 'DTOptionsBuilder', '$q',
         'moment', '$rootScope', 'Corporation', 'Dashboard', '$compile'];

    function DashboardController ($scope, CoreService, Chart, DTColumnBuilder, DTOptionsBuilder, $q,
                             moment, $rootScope, Corporation, Dashboard, $compile) {
        var vm = this;


        vm.unsignedForms = {};
        vm.unsignedEvaluations = {};


        //Variable
        vm.facility = CoreService.getCurrentFacility();
        vm.dtInstanceForms = {};
        vm.dtInstanceEvaluations = {};


        $rootScope.$on('bluebookApp:setCurrentFacility', function(event, result) {
            vm.facility = result;
        });


        var facility = CoreService.getCurrentFacility();
        if (facility) {
           // getUnsignedFormsReport();
        }


        function loadAll() {
            if(vm.facility){
                if(!CoreService.getCorporation()){
                    Corporation.get({id: 1}, function (result) {
                        CoreService.setCorporation(result);
                    });
                }
            }
        }

        vm.dtOptionsForms = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Dashboard.unsignedForms({fId : vm.facility.id}, function(result) {
                    vm.unsignedForms = result;
                    defer.resolve(result);
                    prepareDataForms(result);
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

        vm.dtColumnsForms = [

            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.fullNameCapital ? data.fullNameCapital : "Empty";
            }),
            DTColumnBuilder.newColumn(null).withTitle('MrNo').renderWith(function (data, type, full) {
                return data.mrNo;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Form Name').renderWith(function (data, type, full) {
                return data.efname;
            }),

            DTColumnBuilder.newColumn(null).withTitle('Actions').renderWith(function (data, type, full) {
                return actionsFormHtml(data);
            })

        ];


        function actionsFormHtml(data){
            return '<a class="btn-sm btn-primary" ' +
                ' href="#/patient/' + data.id + '/invoice/' + data.efid +'"' + '><i class="fa fa-envira"></i></a>';
        }

        function actionsEvaluationHtml(data){
            return '<a class="btn-sm btn-primary" ' +
                ' href="#/patient/' + data.id + '/evaluationr/' + data.efid +'"' + '><i class="fa fa-envira"></i></a>';
        }

        function takenHtml(data) {
            return '&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-success" ng-click="vm.changeTakenYes(' + data.id + ')" ' +
                'style="cursor: pointer;">Yes</span></td>&nbsp;&nbsp;&nbsp;&nbsp;' +
                '<td><span class="label label-danger" ng-click="vm.changeTakenNo('+ data.id +')" ' +
                'style="cursor: pointer;" role="button" tabindex="0">No</span></td>';
        }

        function searchForms() {
            vm.dtInstanceForms.reloadData();
        }


        function prepareDataForms(data) {
            // for(var i=0; i < data.length; i++){
            //     data[i].fullNameCapital = CoreService.fullNameCapitalized(data[i].chart.patient);
            // }

            CoreService.fullNameCapitalizedList(data);
        }

        function getUnsignedFormsReport(){

            var filter = {fId : vm.facility.id};

            Dashboard.unsignedForms(filter, function(result) {
                vm.unsignedForms = result;

            });

        }



        /*******************************************************************/
        vm.dtOptionsEvaluations = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Dashboard.unsignedEvaluations({fId : vm.facility.id}, function(result) {
                    vm.unsignedEvaluations = result;
                    defer.resolve(result);
                    prepareDataEvaluations(result);
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

        vm.dtColumnsEvaluations = [
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.fullNameCapital ? data.fullNameCapital : "Empty";
            }),
            DTColumnBuilder.newColumn(null).withTitle('MrNo').renderWith(function (data, type, full) {
                return data.mrNo;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Evaluation Name').renderWith(function (data, type, full) {
                return data.efname;
            }),

            DTColumnBuilder.newColumn(null).withTitle('Actions').renderWith(function (data, type, full) {
                return actionsEvaluationHtml(data);
            })

        ];

        function searchEvaluations() {
            vm.dtInstanceEvaluations.reloadData();
        }

        function prepareDataEvaluations(data) {
            // for(var i=0; i < data.length; i++){
            //     data[i].fullNameCapital = CoreService.fullNameCapitalized(data[i].chart.patient);
            // }

            CoreService.fullNameCapitalizedList(data);

        }


        }



})();
