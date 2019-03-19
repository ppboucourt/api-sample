(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartController', ChartController);

    ChartController.$inject = ['$scope', '$state', 'DataUtils', 'Chart', 'ChartSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartController ($scope, $state, DataUtils, Chart, ChartSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.charts = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Chart.query(function(result) {
                  vm.charts = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.charts, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('mrNo').withTitle('Mr No.'),
              DTColumnBuilder.newColumn('admissionDate').withTitle('Admission Date'),
              DTColumnBuilder.newColumn('dischargeTime').withTitle('Discharge Time'),
              DTColumnBuilder.newColumn('dischargeTo').withTitle('Discharge To'),
              DTColumnBuilder.newColumn('dischargeType').withTitle('Discharge Type'),
              //DTColumnBuilder.newColumn('program').withTitle('Program'),
              //DTColumnBuilder.newColumn('rep_name').withTitle('Rep_name'),
              //DTColumnBuilder.newColumn('referrer').withTitle('Referrer'),
              //DTColumnBuilder.newColumn('marital_status').withTitle('Marital_status'),
              //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
              //DTColumnBuilder.newColumn('race').withTitle('Race'),
              //DTColumnBuilder.newColumn('ethnicity').withTitle('Ethnicity'),
              //DTColumnBuilder.newColumn('address').withTitle('Address'),
              //DTColumnBuilder.newColumn('address_two').withTitle('Address_two'),
              //DTColumnBuilder.newColumn('city').withTitle('City'),
              //DTColumnBuilder.newColumn('state').withTitle('State'),
              //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
              //DTColumnBuilder.newColumn('payment_method').withTitle('Payment_method'),
              //DTColumnBuilder.newColumn('date_first_contact').withTitle('Date_first_contact'),
              //DTColumnBuilder.newColumn('first_contact_name').withTitle('First_contact_name'),
              //DTColumnBuilder.newColumn('first_contact_relationship').withTitle('First_contact_relationship'),
              //DTColumnBuilder.newColumn('occupancy').withTitle('Occupancy'),
              //DTColumnBuilder.newColumn('employer').withTitle('Employer'),
              //DTColumnBuilder.newColumn('employer_phone').withTitle('Employer_phone'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
              //DTColumnBuilder.newColumn('mr_no').withTitle('Mr_no'),
              //DTColumnBuilder.newColumn('first_contact_phone').withTitle('First_contact_phone'),
              //DTColumnBuilder.newColumn('alt_phone').withTitle('Alt_phone'),
              //DTColumnBuilder.newColumn('email').withTitle('Email'),
              //DTColumnBuilder.newColumn('sobriety_date').withTitle('Sobriety_date'),
              //DTColumnBuilder.newColumn('picture').withTitle('Picture'),
              //DTColumnBuilder.newColumn('patient_license').withTitle('Patient_license'),
              //DTColumnBuilder.newColumn('one_time_only').withTitle('One_time_only'),
              //DTColumnBuilder.newColumn('referrer_required_contact').withTitle('Referrer_required_contact'),
              //DTColumnBuilder.newColumn('waitingRoom').withTitle('WaitingRoom'),
              //DTColumnBuilder.newColumn('dischargeDate').withTitle('DischargeDate'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Chart.query(function(result) {
                vm.charts = result;
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
