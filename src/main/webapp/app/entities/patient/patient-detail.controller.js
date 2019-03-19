(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientDetailController', PatientDetailController);

    PatientDetailController.$inject = ['$state', 'chart', 'Chart', '$sessionStorage', 'TAB',
        '$anchorScroll', '$location', '$window', 'fancyboxService', '$scope', '$rootScope'];

    function PatientDetailController($state, chart, Chart, $sessionStorage, TAB, $anchorScroll, $location, $window, fancyboxService,
                                     $scope, $rootScope) {
        var vm = this;
        $sessionStorage.ourl = 'patient-orders';

        //Functions
        vm.chart = chart;
        vm.maritalStatus = chart.typeMaritalStatus ? [chart.typeMaritalStatus] : [];
        vm.ethnicity = chart.typeEthnicity ? [chart.typeEthnicity] : [];
        // vm.race = TypeRace.query();
        // vm.states = CountryState.query();
        vm.typePatientPrograms = chart.typePatientPrograms ? [chart.typePatientPrograms] : [];
        vm.paymentMethod = chart.typePaymentMethods ? [chart.typePaymentMethods] : [];
        vm.icd10s = chart.icd10S;

        vm.beds = vm.chart.bed ? [vm.chart.bed] : [];
        vm.carriers = [];

        // vm.insuranceType = InsuranceType.query();
        // vm.insuranceLevels = InsuranceLevel.query();
        // vm.insuranceRelations = InsuranceRelation.query();

        vm.typeDischargeType = chart.typeDischargeType ? [chart.typeDischargeType] : [];
        vm.typeLevelCare = chart.typeLevelCare ? [chart.typeLevelCare] : [];
        //vm.typeLevelCare = TypeLevelCare.byFacility({id: CoreService.getCurrentFacility().id});
        // vm.insuranceLoaded = InsuranceCarrier.query();

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dateBirth = false;
        vm.datePickerOpenStatus.sobrietyDate = false;
        vm.datePickerOpenStatus.admissionDate = false;
        vm.datePickerOpenStatus.dischargeDate = false;
        vm.datePickerOpenStatus.insuranceDob = false;
        vm.datePickerOpenStatus.dateFirstContact = false;
        vm.firstLevelTabs = true;
        vm.secondLevelTabs = false;
        vm.datePickerOpenStatus.insuranceDob = [false, false];

        vm.activeTab = $sessionStorage.activePatientTab ? $sessionStorage.activePatientTab.activeTab : 0;

        //Subscribe to get totals face sheet and unsubscribe when the scope is destroyed
        $scope.$on('$destroy', $rootScope.$on('bluebookApp:allergiesCount', function (event, data) {
            vm.allergiesCount = data.total;
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:dietsCount', function (event, data) {
            vm.dietsCount = data.total;
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:careTeamsCount', function (event, data) {
            vm.careTeamsCount = data.total;
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:contactsCount', function (event, data) {
            vm.contactsCount = data.total;
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:concurrentReviewCount', function (event, data) {
            vm.concurrentReviewCount = data.total;
        }));

        vm.gotoAnchor = function (x) {
            $anchorScroll.yOffset = 50;   // always scroll by 50 extra pixels
            var newHash = 'anchor_' + x;
            if ($location.hash() !== newHash) {
                // set the $location.hash to `newHash` and
                // $anchorScroll will automatically scroll to it
                $location.hash('anchor_' + x);
            } else {
                // call $anchorScroll() explicitly,
                // since $location.hash hasn't changed
                $anchorScroll();
            }
        };


        vm.goToTop = function () {
            $window.scrollTo(0, 0);
        }

        vm.imageDetail = function (picture, name) {
            fancyboxService.fancyboxPlus()({
                'href': 'data:' + picture.pictureContentType + ';base64,' + picture.picture,
                'title': name,
                // 'titlePosition'  : 'over',
                'transitionIn': 'elastic',
                'transitionOut': 'elastic'
            });

        }


        if (vm.activeTab != TAB.CONCURRENT_REVIEW && vm.activeTab != 16) {//16 is Care Team
            vm.activeTab = (vm.activeTab != 1 && vm.activeTab < 11) ? vm.activeTab : 0;
        }

        //Update the waitingRoom on the Chart
        function updateWaitingChart() {
            vm.isSaving = true;
            Chart.updateWaiting(vm.chart, onUpdateWaitingSuccess, onUpdateWaitingError)
        }

        function onUpdateWaitingSuccess(result) {
            vm.isSaving = !vm.isSaving;
            $state.go($state.current, {}, {reload: true}); //second parameter is for $stateParams
            keepActiveTab(0);
        }

        function onUpdateWaitingError(error) {
            vm.isSaving = !vm.isSaving;
        }

        vm.keepActiveTab = function ($index) {
            $sessionStorage.activePatientTab = {activeTab: $index};
            if ($index > 9) {
                switch ($index) {
                    //Mars Tab
                    case 16:
                        $scope.$emit('bluebookApp:clickedMarsTab', true);
                        break;
                    // case 12:
                    //     $state.go('patient-mars', {id: vm.chart.id}, {reload: true});
                    //     break;
                    default:
                        break;
                }
            }
        }
    }
})();
