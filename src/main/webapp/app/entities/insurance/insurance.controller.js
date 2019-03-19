(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceController', InsuranceController);

    InsuranceController.$inject = ['$scope', '$state', 'Insurance', 'InsuranceSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function InsuranceController ($scope, $state, Insurance, InsuranceSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Insurances';
        vm.insurances = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Insurance.query(function(result) {
                  vm.insurances = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.insurances, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('address').withTitle('Address'),
              DTColumnBuilder.newColumn('address2').withTitle('Address2'),
              DTColumnBuilder.newColumn('zipCode').withTitle('ZipCode'),
              DTColumnBuilder.newColumn('city').withTitle('City'),
              DTColumnBuilder.newColumn('policyNumber').withTitle('PolicyNumber'),
              //DTColumnBuilder.newColumn('groupNumber').withTitle('GroupNumber'),
              //DTColumnBuilder.newColumn('groupName').withTitle('GroupName'),
              //DTColumnBuilder.newColumn('planNumber').withTitle('PlanNumber'),
              //DTColumnBuilder.newColumn('lastName').withTitle('LastName'),
              //DTColumnBuilder.newColumn('firstName').withTitle('FirstName'),
              //DTColumnBuilder.newColumn('middleInitial').withTitle('MiddleInitial'),
              //DTColumnBuilder.newColumn('dob').withTitle('Dob'),
              //DTColumnBuilder.newColumn('gender').withTitle('Gender'),
              //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
              //DTColumnBuilder.newColumn('employer').withTitle('Employer'),
              //DTColumnBuilder.newColumn('insurance_order').withTitle('Insurance_order'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Insurance.query(function(result) {
                vm.insurances = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
