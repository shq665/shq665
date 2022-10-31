package com.example.abcd.presentation.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.abcd.R
import com.example.abcd.databinding.FragmentMapsBinding
import com.example.abcd.domain.model.Character
import com.example.abcd.presentation.BaseFragment
import com.example.abcd.presentation.ext.hasPermission
import com.example.abcd.presentation.model.Lce
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsFragment(override val titleFragment: String) : BaseFragment(R.layout.fragment_maps) {

    override val hasHomeButton: Boolean
        get() = true

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<MapViewModel>()

    private var googleMap: GoogleMap? = null

    @SuppressLint("NewApi")
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                setLocationEnabled(true)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

            }
            else -> {
                setLocationEnabled(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMapsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        initGoogleMap { map ->

            viewModel.dataFlow
                .onEach { lceState ->
                    if(lceState is Lce.Content){
                        lceState.data.onEach {
                        map.addMarker(
                            MarkerOptions().position(it.location)
                         )
                        }
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)


            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(DEFAULT_CAMERA_LAT, DEFAULT_CAMERA_LNG), DEFAULT_CAMERA_ZOOM))

        }

        viewModel
            .selectedMarkerFlow
            .onEach {
                with(binding.bottomSheetContent) {
                    val chInfo = it.characterInfo
                    detailsName.text = it.name
                    detailsNickname.text = chInfo.nickname
                    detailsBirthday.text = chInfo.birthday
                    detailsCategory.text = chInfo.category
                    detailsPortrayed.text = chInfo.portrayed
                    detailsStatus.text = chInfo.status
                }
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.map.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.map.onDestroy()
        googleMap = null
        _binding = null
    }

    private fun initGoogleMap(action: (GoogleMap) -> Unit) {
        binding.map.getMapAsync { map ->
            googleMap = map.apply {

                uiSettings.isCompassEnabled = true
                uiSettings.isZoomControlsEnabled = true

                setOnMarkerClickListener {
                    viewModel.onMarkerClicked(LatLng(it.position.latitude, it.position.longitude))
                    true
                }
            }

            if (requireContext().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                setLocationEnabled(true)
            } else {
                locationPermissionRequest.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }

            action(map)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationEnabled(enabled: Boolean) {
        googleMap?.isMyLocationEnabled = enabled
        googleMap?.uiSettings?.isMyLocationButtonEnabled = enabled
    }

    companion object {
        private const val DEFAULT_CAMERA_ZOOM = 10f
        private const val DEFAULT_CAMERA_LAT = 53.8
        private const val DEFAULT_CAMERA_LNG = 27.45
    }

}