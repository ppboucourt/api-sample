/**
 * Created by PpTMUnited on 1/16/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartAddController',GroupSessionDetailsChartAddController);

    GroupSessionDetailsChartAddController.$inject = ['entity', '$scope', '$q', '$compile', '$uibModalInstance', '$sessionStorage', 'Chart', 'DTColumnBuilder', 'DTOptionsBuilder',
        'CoreService', 'GroupSessionDetails'];

    function GroupSessionDetailsChartAddController(entity, $scope, $q, $compile, $uibModalInstance, $sessionStorage,  Chart, DTColumnBuilder, DTOptionsBuilder,
        CoreService, GroupSessionDetails) {
        var vm = this;

        vm.groupSessionDetails = entity;
        vm.selectedAllCharts = false;



        vm.clear = clear;
        vm.dtInstance = {};
        vm.selectedCharts = {};


        var titleHtmlConsents = '<input type="checkbox" ng-model="vm.selectAllCharts" ng-change="vm.toggleAllCharts()">';
        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle(titleHtmlConsents).notSortable().renderWith(function(data, type, full, meta) {
                vm.selectedCharts[full.id] = false;

                return '<input type="checkbox" ng-model="vm.selectedCharts[' + data.id + ']" ng-click="vm.toggleOneChart()">';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Name').withOption('width', '30%').renderWith(function (data, type, full) {
                return "   " + data.firstName + " " + data.lastName
            }),
            DTColumnBuilder.newColumn('mrNo').withTitle('MrNo').withOption('width', '20%'),
            DTColumnBuilder.newColumn('phone').withTitle('Phone').withOption('width', '15'),
            DTColumnBuilder.newColumn('bed').withTitle('Bed Name').withOption('width', '15%')
        ];



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.toggleOneChart = function () {
            for (var id in vm.selectedCharts) {
                if (vm.selectedCharts.hasOwnProperty(id)) {
                    if(!vm.selectedCharts[id]) {
                        //  vm.selectedAllCharts = false;
                        return;
                    }
                }
            }
        }


        vm.toggleAllCharts = function() {

            if(vm.selectedAllCharts){
                for (var id in vm.selectedCharts) {
                    if (vm.selectedCharts.hasOwnProperty(id)) {
                        vm.selectedCharts[id] = false;
                    }
                }
                vm.selectedAllCharts = false;
            }else{
                for (var id in vm.selectedCharts) {
                    if (vm.selectedCharts.hasOwnProperty(id)) {
                        vm.selectedCharts[id] = true;
                    }
                }
                vm.selectedAllCharts = true;
            }

        }



        vm.addPatients= function(){

            var ids = [];
            for(var id in vm.selectedCharts){
                if(vm.selectedCharts[id] == true){
                    ids.push(id);
                }
            }

            var CollectedBody = {ownerId: vm.groupSessionDetails.id, ids:ids };

            GroupSessionDetails.assignChartsToGroupSessionDetails(CollectedBody, function(data){
                $uibModalInstance.close(true);
            }, function(error){
                console.log(error);
            })
        }


        function diffData (result) {

            var newResult = [];

            for(var i=0; i <  result.length; i++){
                if(!isIn(result[i])){
                    newResult.push(result[i]) ;
                }
            }

            return newResult;
        }

        function isIn(element){

            for(var i=0; i < vm.selectedChartsFromDB.length; i++){

                if(element.id == vm.selectedChartsFromDB[i].chartId){
                    return true;
                }
            }

            return false;
        }




        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            if (!vm.searchQuery || vm.searchQuery == '') {
                vm.facility = CoreService.getCurrentFacility();


                GroupSessionDetails.groupSessionDetailsListChartsVO({id: vm.groupSessionDetails.id}, function(data){
                    vm.selectedChartsFromDB = data ? data : [];

                    Chart.chartsCurrentPatientsVOForGroupSession({id: vm.facility.id}, function (result) {
                        var newResult =diffData(result);

                        defer.resolve(newResult);
                    });

                }, function(error){
                    console.log(error);
                });



            } else {
                defer.resolve($filter('filter')(vm.dtInstance, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            })
            .withOption('headerCallback', function(header) {
                if (!vm.headerCompiled) {
                    vm.headerCompiled = true;
                    $compile(angular.element(header).contents())($scope);
                }
            });






    }
})();
