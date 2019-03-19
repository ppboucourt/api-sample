(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FaxSendLogController', FaxSendLogController);

    FaxSendLogController.$inject = ['serviceProviders', '$compile', 'Base64', '$localStorage', '$sessionStorage', 'CoreService', 'DTOptionsBuilder', 'DTColumnBuilder', '$filter', '$q', 'GUIUtils', '$scope', '$state', 'FaxSendLog', 'FaxSendLogSearch', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function FaxSendLogController(serviceProviders, $compile, Base64, $localStorage, $sessionStorage, CoreService, DTOptionsBuilder, DTColumnBuilder, $filter, $q, GUIUtils, $scope, $state, FaxSendLog, FaxSendLogSearch, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        vm.serviceProviders = serviceProviders;
        console.log(vm.serviceProviders)


        vm.search = search;
        vm.loadAll = loadAll;
        vm.searchQuery = pagingParams.search;
        vm.currentSearch = pagingParams.search;

        vm.viewPdf = viewPdf;



        /************************************************/
        vm.faxSendLogs = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        function loadAll() {
            FaxSendLog.query({fId:CoreService.getCurrentFacility()}, function (result) {
                vm.faxSendLogs = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta) {
            var domain = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
            var url = domain + '/fax/pdf-download/' + data.id + "/" + Base64.encode($localStorage.authenticationToken || $sessionStorage.authenticationToken);

            var stButtons = '';
            stButtons += '<a class="btn btn-sm btn-primary"  title="View Details"  href="#/' + 'fax-send-log' + '/' + data.id + '">' +
                '   <i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;';

            stButtons += '<a class="btn btn-sm btn-success"     title="View Pdf" href="' + url + '"   download="eFax.pdf"  >' +
                '   <i class="glyphicon glyphicon-save"></i></a>&nbsp;';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }


        vm.refresh = function () {
            vm.isSaving = true;
            FaxSendLog.updateStatus(function (result) {
                if (result.value == true) {
                    vm.dtInstance.reloadData();
                }

                vm.isSaving = false;
            });
        }

        function viewPdf(data) {
            var domain = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '');
            var url = domain + '/fax/pdf-download/' + data.id + "/" + Base64.encode($localStorage.authenticationToken || $sessionStorage.authenticationToken);
            return url;
        }


        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                FaxSendLog.query({fId:CoreService.getCurrentFacility().id}, function (result) { //                ServiceProviderSearch.query(function (result) { //
                    vm.faxSendLogs = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.faxSendLogs, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('FaxTo').renderWith(function (data) {

                var dataFax = data.faxToNumber.replace('+1', '');
                    dataFax = dataFax.replace(/-/g, '');

                for (var i = 0; i < vm.serviceProviders.length; i++) {
                    var sFax = vm.serviceProviders[i].fax.replace(/-/g, '');

                    if (dataFax == sFax) {
                        return vm.serviceProviders[i].name;
                    }
                }

                return data.toName;

            }),
            DTColumnBuilder.newColumn('chartFullName').withTitle('Patient'),
            DTColumnBuilder.newColumn('sendByFullName').withTitle('SendBy'),
            DTColumnBuilder.newColumn('faxToNumber').withTitle('FaxNumber'),
            DTColumnBuilder.newColumn(null).withTitle('Send Date').renderWith(function (data) {
                return CoreService.parseToDateTime(data.sendDate);
            }),
            DTColumnBuilder.newColumn('faxState').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];


    }
})();
